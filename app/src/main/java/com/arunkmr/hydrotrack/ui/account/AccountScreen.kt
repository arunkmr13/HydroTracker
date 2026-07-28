package com.arunkmr.hydrotrack.ui.account

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arunkmr.hydrotrack.auth.GoogleAuthManager
import com.arunkmr.hydrotrack.ui.settings.SettingsViewModel
import com.arunkmr.hydrotrack.ui.theme.EncouragingGreen
import com.arunkmr.hydrotrack.ui.theme.GlassBackground
import com.arunkmr.hydrotrack.ui.theme.GlassCard
import com.arunkmr.hydrotrack.ui.theme.HydroTeal
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun AccountScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val firebaseUser = GoogleAuthManager.getCurrentFirebaseUser()

    var showEmailAuthDialog by remember { mutableStateOf(false) }
    var isSignUpMode by remember { mutableStateOf(false) }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }
    var authErrorMessage by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val account = GoogleAuthManager.parseSignInResult(task)
            ?: GoogleSignIn.getLastSignedInAccount(context)

        if (account != null) {
            GoogleAuthManager.authenticateWithFirebase(account) { success, email, name, _ ->
                if (success) {
                    viewModel.onGoogleSignInSuccess(email = email, name = name)
                }
            }
        }
    }

    GlassBackground(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "COMMERCIAL USER PROFILE",
                style = MaterialTheme.typography.labelMedium,
                color = HydroTeal,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Account & Cloud Storage",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Commercial User Profile Glass Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 24.dp,
                contentPadding = 20.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        shape = CircleShape,
                        color = HydroTeal.copy(alpha = 0.2f),
                        modifier = Modifier.size(76.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = (uiState.userName ?: "U").take(1).uppercase(),
                                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                                color = HydroTeal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.userName ?: "Guest User",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = uiState.userEmail ?: "Local device storage only",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cloud Storage Connection Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (uiState.isSignedIn) EncouragingGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = null,
                                tint = if (uiState.isSignedIn) EncouragingGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (uiState.isSignedIn) "Firebase Firestore Synchronized 🟢" else "Local Database Only",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = if (uiState.isSignedIn) EncouragingGreen else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (firebaseUser != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "UID: ${firebaseUser.uid}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Commercial Authentication Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp,
                contentPadding = 16.dp
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = HydroTeal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Commercial Authentication",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (uiState.isSignedIn) {
                        OutlinedButton(
                            onClick = { viewModel.onSignOut(context) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign Out of Account")
                        }
                    } else {
                        Button(
                            onClick = {
                                val client = GoogleAuthManager.getGoogleSignInClient(context)
                                launcher.launch(client.signInIntent)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HydroTeal)
                        ) {
                            Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign In with Google Account")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                isSignUpMode = false
                                authErrorMessage = null
                                showEmailAuthDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign In with Email / Password")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Commercial Application Details Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp,
                contentPadding = 16.dp
            ) {
                Column {
                    Text(
                        text = "HydroTrack Commercial Release",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Version 1.0.0 (Build 100) • Medical Hydration Suite",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    // Email Authentication Dialog
    if (showEmailAuthDialog) {
        AlertDialog(
            onDismissRequest = { showEmailAuthDialog = false },
            title = {
                Text(if (isSignUpMode) "Create Commercial Account" else "Sign In with Email")
            },
            text = {
                Column {
                    if (isSignUpMode) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Email Address") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )

                    authErrorMessage?.let { err ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isSignUpMode) {
                            GoogleAuthManager.signUpWithEmail(emailInput, passwordInput, nameInput) { success, err ->
                                if (success) {
                                    viewModel.onGoogleSignInSuccess(emailInput, nameInput.ifBlank { "HydroTrack User" })
                                    showEmailAuthDialog = false
                                } else {
                                    authErrorMessage = err
                                }
                            }
                        } else {
                            GoogleAuthManager.signInWithEmail(emailInput, passwordInput) { success, err ->
                                if (success) {
                                    val user = GoogleAuthManager.getCurrentFirebaseUser()
                                    viewModel.onGoogleSignInSuccess(emailInput, user?.displayName ?: "HydroTrack User")
                                    showEmailAuthDialog = false
                                } else {
                                    authErrorMessage = err
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HydroTeal)
                ) {
                    Text(if (isSignUpMode) "Register" else "Sign In")
                }
            },
            dismissButton = {
                TextButton(onClick = { isSignUpMode = !isSignUpMode; authErrorMessage = null }) {
                    Text(if (isSignUpMode) "Have account? Sign In" else "New user? Register")
                }
            }
        )
    }
}
