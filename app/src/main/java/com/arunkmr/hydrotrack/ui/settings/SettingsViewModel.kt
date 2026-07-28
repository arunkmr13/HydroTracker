package com.arunkmr.hydrotrack.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import com.arunkmr.hydrotrack.data.repository.WaterRepository
import com.arunkmr.hydrotrack.reminder.ReminderScheduler
import com.arunkmr.hydrotrack.ui.export.ExportUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val waterRepository: WaterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                if (settings != null) {
                    _uiState.update {
                        it.copy(
                            weightInput = settings.weightKg.toString(),
                            bottleSizeInput = settings.bottleSizeMl.toString(),
                            useManualOverride = settings.useManualOverride,
                            manualTargetInput = settings.manualTargetMl?.toString() ?: "",
                            reminderIntervalMinutes = settings.reminderIntervalMinutes,
                            sleepStartHour = settings.sleepStartHour,
                            sleepEndHour = settings.sleepEndHour,
                            themeMode = settings.themeMode,
                            userEmail = settings.userEmail,
                            userName = settings.userName,
                            isSignedIn = settings.isSignedIn,
                            currentSettings = settings,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun checkExistingGoogleAccount(context: Context) {
        val existing = com.arunkmr.hydrotrack.auth.GoogleAuthManager.checkExistingAccount(context)
        if (existing != null && !_uiState.value.isSignedIn) {
            viewModelScope.launch {
                settingsRepository.updateAccountInfo(email = existing.first, name = existing.second, isSignedIn = true)
            }
        }
    }

    fun onGoogleSignInSuccess(email: String?, name: String?) {
        viewModelScope.launch {
            settingsRepository.updateAccountInfo(email = email, name = name, isSignedIn = true)
        }
    }

    fun onSignOut(context: Context) {
        com.arunkmr.hydrotrack.auth.GoogleAuthManager.signOut(context) {
            viewModelScope.launch {
                settingsRepository.updateAccountInfo(email = null, name = null, isSignedIn = false)
            }
        }
    }

    fun clearAllData(context: Context) {
        viewModelScope.launch {
            waterRepository.clearAllData(context)
        }
    }

    fun onThemeModeChanged(mode: String) {
        _uiState.update { it.copy(themeMode = mode, isSaved = false) }
        viewModelScope.launch {
            settingsRepository.updateThemeMode(mode)
        }
    }

    fun onWeightChanged(weight: String) {
        _uiState.update { it.copy(weightInput = weight, isSaved = false) }
    }

    fun onBottleSizeChanged(size: String) {
        _uiState.update { it.copy(bottleSizeInput = size, isSaved = false) }
    }

    fun onManualOverrideToggled(enabled: Boolean) {
        _uiState.update { it.copy(useManualOverride = enabled, isSaved = false) }
    }

    fun onManualTargetChanged(target: String) {
        _uiState.update { it.copy(manualTargetInput = target, isSaved = false) }
    }

    fun onReminderIntervalChanged(minutes: Int) {
        _uiState.update { it.copy(reminderIntervalMinutes = minutes, isSaved = false) }
    }

    fun saveSettings(context: Context) {
        val state = _uiState.value
        val weight = state.weightInput.toFloatOrNull() ?: 70f
        val bottleSize = state.bottleSizeInput.toIntOrNull() ?: 850
        val manualTarget = if (state.useManualOverride) state.manualTargetInput.toIntOrNull() else null

        viewModelScope.launch {
            settingsRepository.saveOnboardingSettings(
                weightKg = weight,
                bottleSizeMl = bottleSize,
                manualTargetMl = manualTarget,
                useManualOverride = state.useManualOverride
            )

            settingsRepository.updateReminderSettings(
                intervalMinutes = state.reminderIntervalMinutes,
                sleepStartHour = state.sleepStartHour,
                sleepEndHour = state.sleepEndHour
            )

            // Update WorkManager schedule
            ReminderScheduler.schedulePeriodicReminders(context, state.reminderIntervalMinutes)

            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun exportDoctorReport(context: Context) {
        viewModelScope.launch {
            val settings = settingsRepository.getSettingsSync() ?: return@launch
            val startDate = LocalDate.now().minusDays(29)
            val endDate = LocalDate.now()

            val totals = waterRepository.getDailyTotals(startDate, endDate).firstOrNull() ?: emptyList()
            val symptoms = waterRepository.getSymptomsInRange(startDate, endDate).firstOrNull() ?: emptyList()

            ExportUtil.shareCsvReport(
                context = context,
                dailyTotals = totals,
                symptoms = symptoms,
                settings = settings
            )
        }
    }
}
