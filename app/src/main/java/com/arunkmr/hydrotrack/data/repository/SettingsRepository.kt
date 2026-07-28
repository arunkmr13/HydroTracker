package com.arunkmr.hydrotrack.data.repository

import com.arunkmr.hydrotrack.data.local.SettingsDao
import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt

class SettingsRepository(
    private val settingsDao: SettingsDao
) {

    fun getSettings(): Flow<UserSettingsEntity?> {
        return settingsDao.getSettings()
    }

    suspend fun getSettingsSync(): UserSettingsEntity? {
        return settingsDao.getSettingsSync()
    }

    /**
     * Save onboarding / profile settings.
     */
    suspend fun saveOnboardingSettings(
        weightKg: Float,
        bottleSizeMl: Int,
        manualTargetMl: Int?,
        useManualOverride: Boolean
    ) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        val calculatedTarget = (weightKg * 35).roundToInt()
        val isManualActive = useManualOverride && manualTargetMl != null && manualTargetMl > 0

        val settings = current.copy(
            weightKg = weightKg,
            bottleSizeMl = bottleSizeMl,
            calculatedTargetMl = calculatedTarget,
            manualTargetMl = if (isManualActive) manualTargetMl else null,
            useManualOverride = isManualActive,
            onboardingCompleted = true
        )
        settingsDao.insertOrUpdate(settings)
    }

    suspend fun updateWeight(weightKg: Float) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        val updated = current.copy(
            weightKg = weightKg,
            calculatedTargetMl = (weightKg * 35).roundToInt()
        )
        settingsDao.insertOrUpdate(updated)
    }

    suspend fun updateBottleSize(bottleSizeMl: Int) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(current.copy(bottleSizeMl = bottleSizeMl))
    }

    suspend fun setManualTarget(targetMl: Int) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(
            current.copy(
                manualTargetMl = targetMl,
                useManualOverride = true
            )
        )
    }

    suspend fun clearManualTarget() {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(
            current.copy(
                manualTargetMl = null,
                useManualOverride = false
            )
        )
    }

    suspend fun updateReminderSettings(
        intervalMinutes: Int,
        sleepStartHour: Int,
        sleepEndHour: Int
    ) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(
            current.copy(
                reminderIntervalMinutes = intervalMinutes,
                sleepStartHour = sleepStartHour,
                sleepEndHour = sleepEndHour
            )
        )
    }

    suspend fun updateThemeMode(themeMode: String) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(current.copy(themeMode = themeMode))
    }

    suspend fun updateAccountInfo(email: String?, name: String?, isSignedIn: Boolean) {
        val current = settingsDao.getSettingsSync() ?: UserSettingsEntity()
        settingsDao.insertOrUpdate(
            current.copy(
                userEmail = email,
                userName = name,
                isSignedIn = isSignedIn
            )
        )
    }
}
