package com.arunkmr.hydrotrack.data.repository

import com.arunkmr.hydrotrack.data.local.DailyTotal
import com.arunkmr.hydrotrack.data.local.SymptomLogDao
import com.arunkmr.hydrotrack.data.local.WaterLogDao
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId
import androidx.glance.appwidget.updateAll

class WaterRepository(
    private val waterLogDao: WaterLogDao,
    private val symptomLogDao: SymptomLogDao
) {

    /**
     * Log a refill with the given amount. Auto-timestamps to now.
     */
    suspend fun logRefill(amountMl: Int, context: android.content.Context? = null): Long {
        val log = WaterLogEntity(
            timestampEpochMs = System.currentTimeMillis(),
            amountMl = amountMl
        )
        val id = waterLogDao.insert(log)
        val savedLog = log.copy(id = id)

        // Sync to Firebase Cloud & Analytics
        try {
            com.arunkmr.hydrotrack.sync.FirebaseSyncManager.syncWaterLogToCloud(savedLog)
            context?.let { com.arunkmr.hydrotrack.analytics.AnalyticsManager.logWaterRefill(amountMl, 850) }
        } catch (_: Exception) {}

        context?.let {
            try {
                com.arunkmr.hydrotrack.widget.HydroWidget().updateAll(it)
            } catch (_: Exception) {}
        }
        return id
    }

    /**
     * Get all water logs for today as a reactive Flow.
     */
    fun getTodayLogs(): Flow<List<WaterLogEntity>> {
        val (start, end) = getDayBoundaries(LocalDate.now())
        return waterLogDao.getLogsForDay(start, end)
    }

    /**
     * Get total water intake for today in ml as a reactive Flow.
     */
    fun getTodayTotal(): Flow<Int?> {
        val (start, end) = getDayBoundaries(LocalDate.now())
        return waterLogDao.getTotalForDay(start, end)
    }

    /**
     * Get water logs in a date range for history/export.
     */
    fun getLogsInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<WaterLogEntity>> {
        val startMs = getDayBoundaries(startDate).first
        val endMs = getDayBoundaries(endDate).second
        return waterLogDao.getLogsInRange(startMs, endMs)
    }

    /**
     * Get daily totals grouped by day for heatmap display.
     */
    fun getDailyTotals(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyTotal>> {
        val startMs = getDayBoundaries(startDate).first
        val endMs = getDayBoundaries(endDate).second
        return waterLogDao.getDailyTotals(startMs, endMs)
    }

    /**
     * Get the timestamp of the last logged water intake.
     * Used for smart reminder suppression (suppress for 90 min after a log).
     */
    suspend fun getLastLogTimestamp(): Long? {
        return waterLogDao.getLastLogTimestamp()
    }

    /**
     * Delete a water log entry (for undo support).
     */
    suspend fun deleteLog(id: Long, context: android.content.Context? = null) {
        waterLogDao.deleteLogById(id)
        context?.let {
            try {
                com.arunkmr.hydrotrack.widget.HydroWidget().updateAll(it)
            } catch (_: Exception) {}
        }
    }

    suspend fun clearAllData(context: android.content.Context? = null) {
        waterLogDao.deleteAllLogs()
        symptomLogDao.deleteAllSymptoms()
        context?.let {
            try {
                com.arunkmr.hydrotrack.widget.HydroWidget().updateAll(it)
            } catch (_: Exception) {}
        }
    }

    // --- Symptom logging ---

    suspend fun logSymptom(symptom: SymptomLogEntity): Long {
        return symptomLogDao.insert(symptom)
    }

    suspend fun updateSymptom(symptom: SymptomLogEntity) {
        symptomLogDao.update(symptom)
    }

    fun getTodaySymptom(): Flow<SymptomLogEntity?> {
        val today = LocalDate.now().toEpochDay()
        return symptomLogDao.getLogForDay(today)
    }

    fun getSymptomsInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<SymptomLogEntity>> {
        return symptomLogDao.getLogsInRange(
            startDay = startDate.toEpochDay(),
            endDay = endDate.toEpochDay()
        )
    }

    /**
     * Calculate the start and end epoch milliseconds for a given date
     * in the user's local timezone.
     */
    private fun getDayBoundaries(date: LocalDate): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val startMs = date.atStartOfDay(zone).toInstant().toEpochMilli()
        val endMs = date.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
        return startMs to endMs
    }
}
