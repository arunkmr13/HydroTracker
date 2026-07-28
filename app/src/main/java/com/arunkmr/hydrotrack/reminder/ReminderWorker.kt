package com.arunkmr.hydrotrack.reminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arunkmr.hydrotrack.data.local.AppDatabase
import com.arunkmr.hydrotrack.domain.TargetCalculator
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class ReminderWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val SUPPRESSION_WINDOW_MS = 90 * 60 * 1000L // 90 minutes in ms
    }

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(context)
        val settings = db.settingsDao().getSettingsSync() ?: return Result.success()

        // 1. Check if onboarding is completed
        if (!settings.onboardingCompleted) return Result.success()

        // 2. Check Sleep Window (e.g. 23:00 to 07:00)
        val currentHour = LocalTime.now().hour
        val sleepStart = settings.sleepStartHour // e.g. 23
        val sleepEnd = settings.sleepEndHour     // e.g. 7

        val isSleeping = if (sleepStart > sleepEnd) {
            // Overnight sleep window (e.g. 23 to 7)
            currentHour >= sleepStart || currentHour < sleepEnd
        } else {
            // Same-day sleep window (e.g. 1 to 5)
            currentHour in sleepStart until sleepEnd
        }

        if (isSleeping) {
            return Result.success()
        }

        // 3. Check if today's goal is already met
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now()
        val startMs = today.atStartOfDay(zone).toInstant().toEpochMilli()
        val endMs = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1

        val todayTotal = db.waterLogDao().getTotalForDay(startMs, endMs).firstOrNull() ?: 0
        val targetMl = TargetCalculator.calculateTarget(settings)

        if (todayTotal >= targetMl) {
            // Daily goal reached — no reminder needed
            return Result.success()
        }

        // 4. Smart Suppression Check: Suppress if water was logged within the last 90 minutes
        val lastLogMs = db.waterLogDao().getLastLogTimestamp()
        if (lastLogMs != null) {
            val msSinceLastLog = System.currentTimeMillis() - lastLogMs
            if (msSinceLastLog < SUPPRESSION_WINDOW_MS) {
                // Suppress reminder — user logged recently
                return Result.success()
            }
        }

        // 5. All checks passed! Post hydration reminder notification
        NotificationHelper.showHydrationReminder(
            context = context,
            bottleSizeMl = settings.bottleSizeMl
        )

        return Result.success()
    }
}
