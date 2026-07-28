package com.arunkmr.hydrotrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val weightKg: Float = 70f,
    val bottleSizeMl: Int = 850,
    val calculatedTargetMl: Int = 2450,
    val manualTargetMl: Int? = null,
    val useManualOverride: Boolean = false,
    val reminderIntervalMinutes: Int = 60,
    val sleepStartHour: Int = 23,
    val sleepEndHour: Int = 7,
    val onboardingCompleted: Boolean = false,
    val themeMode: String = "SYSTEM",
    val userEmail: String? = null,
    val userName: String? = null,
    val isSignedIn: Boolean = false
)
