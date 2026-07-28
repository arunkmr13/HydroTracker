package com.arunkmr.hydrotrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import com.arunkmr.hydrotrack.data.repository.WaterRepository
import com.arunkmr.hydrotrack.domain.TargetCalculator
import com.arunkmr.hydrotrack.domain.model.DailyProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val waterRepository: WaterRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        waterRepository.getTodayTotal(),
        waterRepository.getTodayLogs(),
        settingsRepository.getSettings()
    ) { todayTotal, todayLogs, settings ->
        if (settings == null) {
            HomeUiState(isLoading = true)
        } else {
            val currentMl = todayTotal ?: 0
            val targetMl = TargetCalculator.calculateTarget(settings)
            val bottleSizeMl = settings.bottleSizeMl

            val progress = DailyProgress.from(
                currentMl = currentMl,
                targetMl = targetMl,
                bottleSizeMl = bottleSizeMl
            )

            HomeUiState(
                progress = progress,
                bottleSizeMl = bottleSizeMl,
                todayLogs = todayLogs,
                userName = settings.userName,
                userEmail = settings.userEmail,
                isSignedIn = settings.isSignedIn,
                isLoading = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    /**
     * Log a single bottle refill (e.g. +850 ml) with current timestamp.
     */
    fun logRefill() {
        val bottleSize = uiState.value.bottleSizeMl
        viewModelScope.launch {
            waterRepository.logRefill(bottleSize)
        }
    }

    /**
     * Delete a specific water log entry (undo functionality).
     */
    fun deleteLog(id: Long) {
        viewModelScope.launch {
            waterRepository.deleteLog(id)
        }
    }
}
