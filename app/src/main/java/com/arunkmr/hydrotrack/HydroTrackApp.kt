package com.arunkmr.hydrotrack

import android.app.Application
import com.arunkmr.hydrotrack.data.local.AppDatabase

class HydroTrackApp : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    companion object {
        lateinit var instance: HydroTrackApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize Notification Channel
        com.arunkmr.hydrotrack.reminder.NotificationHelper.createNotificationChannel(this)

        // Initialize Firebase Analytics
        com.arunkmr.hydrotrack.analytics.AnalyticsManager.initialize(this)

        // Schedule periodic hydration reminders
        com.arunkmr.hydrotrack.reminder.ReminderScheduler.schedulePeriodicReminders(this)
    }
}
