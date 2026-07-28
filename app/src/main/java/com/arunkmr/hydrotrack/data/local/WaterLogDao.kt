package com.arunkmr.hydrotrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data class for daily total aggregation queries.
 */
data class DailyTotal(
    val dateEpochDay: Long,
    val totalMl: Int
)

@Dao
interface WaterLogDao {

    @Insert
    suspend fun insert(log: WaterLogEntity): Long

    @Query("SELECT * FROM water_logs WHERE timestampEpochMs BETWEEN :dayStartMs AND :dayEndMs ORDER BY timestampEpochMs DESC")
    fun getLogsForDay(dayStartMs: Long, dayEndMs: Long): Flow<List<WaterLogEntity>>

    @Query("SELECT SUM(amountMl) FROM water_logs WHERE timestampEpochMs BETWEEN :dayStartMs AND :dayEndMs")
    fun getTotalForDay(dayStartMs: Long, dayEndMs: Long): Flow<Int?>

    @Query("SELECT * FROM water_logs WHERE timestampEpochMs BETWEEN :startMs AND :endMs ORDER BY timestampEpochMs DESC")
    fun getLogsInRange(startMs: Long, endMs: Long): Flow<List<WaterLogEntity>>

    @Query(
        "SELECT (timestampEpochMs / 86400000) as dateEpochDay, SUM(amountMl) as totalMl " +
        "FROM water_logs " +
        "WHERE timestampEpochMs BETWEEN :startMs AND :endMs " +
        "GROUP BY dateEpochDay " +
        "ORDER BY dateEpochDay"
    )
    fun getDailyTotals(startMs: Long, endMs: Long): Flow<List<DailyTotal>>

    @Query("SELECT MAX(timestampEpochMs) FROM water_logs")
    suspend fun getLastLogTimestamp(): Long?

    @Query("DELETE FROM water_logs WHERE id = :id")
    suspend fun deleteLogById(id: Long)

    @Query("DELETE FROM water_logs")
    suspend fun deleteAllLogs()
}
