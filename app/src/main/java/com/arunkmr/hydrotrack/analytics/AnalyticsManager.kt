package com.arunkmr.hydrotrack.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsManager {

    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun initialize(context: Context) {
        if (firebaseAnalytics == null) {
            try {
                firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            } catch (_: Exception) {}
        }
    }

    fun logWaterRefill(amountMl: Int, bottleSizeMl: Int) {
        val bundle = Bundle().apply {
            putInt("amount_ml", amountMl)
            putInt("bottle_size_ml", bottleSizeMl)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics?.logEvent("water_refill_logged", bundle)
    }

    fun logSymptomCheckin(feltDizzy: Boolean, drySkin: Boolean) {
        val bundle = Bundle().apply {
            putBoolean("felt_dizzy", feltDizzy)
            putBoolean("dry_skin", drySkin)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics?.logEvent("symptom_checkin_logged", bundle)
    }

    fun logDoctorExport() {
        val bundle = Bundle().apply {
            putString("export_format", "CSV")
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics?.logEvent("doctor_report_exported", bundle)
    }

    fun logUserAuth(method: String) {
        val bundle = Bundle().apply {
            putString("method", method)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
    }

    fun logGoalAchieved(totalMl: Int, targetMl: Int) {
        val bundle = Bundle().apply {
            putInt("total_ml", totalMl)
            putInt("target_ml", targetMl)
            putLong("timestamp", System.currentTimeMillis())
        }
        firebaseAnalytics?.logEvent("hydration_goal_achieved", bundle)
    }
}
