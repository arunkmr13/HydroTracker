package com.arunkmr.hydrotrack.reminder

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    private const val PERIODIC_WORK_TAG = "hydrotrack_periodic_reminder"
    private const val SNOOZE_WORK_TAG = "hydrotrack_snooze_reminder"

    /**
     * Schedule periodic hydration reminders.
     * Minimum interval supported by WorkManager is 15 minutes.
     */
    fun schedulePeriodicReminders(context: Context, intervalMinutes: Int = 60) {
        val safeInterval = intervalMinutes.coerceAtLeast(15)

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            repeatInterval = safeInterval.toLong(),
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            PERIODIC_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /**
     * Schedule a 15-minute snooze reminder.
     */
    fun scheduleSnooze(context: Context) {
        val snoozeRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            SNOOZE_WORK_TAG,
            ExistingWorkPolicy.REPLACE,
            snoozeRequest
        )
    }

    /**
     * Cancel all background reminder workers.
     */
    fun cancelReminders(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(PERIODIC_WORK_TAG)
        WorkManager.getInstance(context).cancelUniqueWork(SNOOZE_WORK_TAG)
    }
}
