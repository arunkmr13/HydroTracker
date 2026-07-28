package com.arunkmr.hydrotrack.sync

import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseSyncManager {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    /**
     * Upload a logged water refill to Firebase Firestore.
     */
    fun syncWaterLogToCloud(log: WaterLogEntity) {
        val user = auth.currentUser ?: return
        val logData = hashMapOf(
            "id" to log.id,
            "amountMl" to log.amountMl,
            "timestampEpochMs" to log.timestampEpochMs,
            "syncedAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(user.uid)
            .collection("water_logs")
            .document(log.id.toString())
            .set(logData)
    }

    /**
     * Upload a symptom check-in to Firebase Firestore.
     */
    fun syncSymptomToCloud(symptom: SymptomLogEntity) {
        val user = auth.currentUser ?: return
        val symptomData = hashMapOf(
            "id" to symptom.id,
            "dateEpochDay" to symptom.dateEpochDay,
            "feltDizzy" to symptom.feltDizzy,
            "drySkin" to symptom.drySkin,
            "notes" to symptom.notes,
            "syncedAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(user.uid)
            .collection("symptoms")
            .document(symptom.dateEpochDay.toString())
            .set(symptomData)
    }

    /**
     * Sync user settings to Firebase Firestore.
     */
    fun syncSettingsToCloud(weightKg: Float, targetMl: Int, bottleSizeMl: Int) {
        val user = auth.currentUser ?: return
        val settingsData = hashMapOf(
            "weightKg" to weightKg,
            "targetMl" to targetMl,
            "bottleSizeMl" to bottleSizeMl,
            "updatedAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(user.uid)
            .set(settingsData)
    }
}
