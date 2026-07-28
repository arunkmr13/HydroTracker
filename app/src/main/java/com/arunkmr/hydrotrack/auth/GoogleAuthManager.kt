package com.arunkmr.hydrotrack.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest

object GoogleAuthManager {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun getCurrentFirebaseUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun authenticateWithFirebase(
        account: GoogleSignInAccount,
        onComplete: (Boolean, String?, String?, String?) -> Unit
    ) {
        val idToken = account.idToken
        if (idToken != null) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(true, user?.email ?: account.email, user?.displayName ?: account.displayName, user?.uid)
                } else {
                    onComplete(true, account.email, account.displayName, auth.currentUser?.uid)
                }
            }
        } else {
            if (auth.currentUser == null) {
                auth.signInAnonymously().addOnCompleteListener { task ->
                    val user = auth.currentUser
                    onComplete(true, user?.email ?: account.email ?: "user@hydrotrack.app", user?.displayName ?: account.displayName ?: "HydroTrack User", user?.uid)
                }
            } else {
                val user = auth.currentUser
                onComplete(true, user?.email ?: account.email, user?.displayName ?: account.displayName, user?.uid)
            }
        }
    }

    fun signUpWithEmail(
        email: String,
        pass: String,
        name: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    onComplete(true, null)
                } ?: onComplete(true, null)
            } else {
                onComplete(false, task.exception?.localizedMessage ?: "Sign up failed")
            }
        }
    }

    fun signInWithEmail(
        email: String,
        pass: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(true, null)
            } else {
                onComplete(false, task.exception?.localizedMessage ?: "Authentication failed")
            }
        }
    }

    fun parseSignInResult(completedTask: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return try {
            completedTask.getResult(ApiException::class.java)
        } catch (_: Exception) {
            null
        }
    }

    fun checkExistingAccount(context: Context): Pair<String?, String?>? {
        val fbUser = auth.currentUser
        if (fbUser != null && !fbUser.isAnonymous) {
            return Pair(fbUser.email ?: "Signed in User", fbUser.displayName ?: "HydroTrack User")
        }
        val account = GoogleSignIn.getLastSignedInAccount(context) ?: return null
        val email = account.email
        val name = account.displayName ?: account.givenName ?: "HydroTrack User"
        return Pair(email, name)
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        try {
            auth.signOut()
        } catch (_: Exception) {}
        getGoogleSignInClient(context).signOut().addOnCompleteListener {
            onComplete()
        }
    }
}
