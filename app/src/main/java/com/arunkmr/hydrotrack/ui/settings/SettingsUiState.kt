package com.arunkmr.hydrotrack.ui.settings

import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity

data class SettingsUiState(
    val weightInput: String = "70",
    val bottleSizeInput: String = "850",
    val useManualOverride: Boolean = false,
    val manualTargetInput: String = "",
    val reminderIntervalMinutes: Int = 60,
    val sleepStartHour: Int = 23,
    val sleepEndHour: Int = 7,
    val themeMode: String = "SYSTEM",
    val userEmail: String? = null,
    val userName: String? = null,
    val isSignedIn: Boolean = false,
    val isSaved: Boolean = false,
    val isLoading: Boolean = true,
    val currentSettings: UserSettingsEntity? = null
)
