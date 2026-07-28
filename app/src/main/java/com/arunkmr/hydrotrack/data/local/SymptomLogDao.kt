package com.arunkmr.hydrotrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SymptomLogDao {

    @Insert
    suspend fun insert(log: SymptomLogEntity): Long

    @Update
    suspend fun update(log: SymptomLogEntity)

    @Query("SELECT * FROM symptom_logs WHERE dateEpochDay = :dateEpochDay LIMIT 1")
    fun getLogForDay(dateEpochDay: Long): Flow<SymptomLogEntity?>

    @Query("SELECT * FROM symptom_logs WHERE dateEpochDay BETWEEN :startDay AND :endDay ORDER BY dateEpochDay")
    fun getLogsInRange(startDay: Long, endDay: Long): Flow<List<SymptomLogEntity>>

    @Query("DELETE FROM symptom_logs")
    suspend fun deleteAllSymptoms()
}
