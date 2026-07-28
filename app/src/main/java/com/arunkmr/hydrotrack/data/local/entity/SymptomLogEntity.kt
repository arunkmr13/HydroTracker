package com.arunkmr.hydrotrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptom_logs")
data class SymptomLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateEpochDay: Long,
    val feltDizzy: Boolean = false,
    val drySkin: Boolean = false,
    val notes: String? = null
)
