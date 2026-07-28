package com.arunkmr.hydrotrack.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        // Load initial settings if available
        viewModelScope.launch {
            val existing = settingsRepository.getSettingsSync()
            if (existing != null) {
                _uiState.update {
                    it.copy(
                        weightInput = existing.weightKg.toString(),
                        bottleSizeInput = existing.bottleSizeMl.toString(),
                        useManualOverride = existing.useManualOverride,
                        manualTargetInput = existing.manualTargetMl?.toString() ?: ""
                    )
                }
            }
        }
    }

    fun saveGoogleAccount(email: String?, name: String?) {
        viewModelScope.launch {
            settingsRepository.updateAccountInfo(email = email, name = name, isSignedIn = true)
        }
    }

    fun onWeightChanged(weight: String) {
        _uiState.update { it.copy(weightInput = weight) }
    }

    fun onBottleSizeChanged(size: String) {
        _uiState.update { it.copy(bottleSizeInput = size) }
    }

    fun onPresetBottleSizeSelected(sizeMl: Int) {
        _uiState.update { it.copy(bottleSizeInput = sizeMl.toString()) }
    }

    fun onManualOverrideToggled(enabled: Boolean) {
        _uiState.update { it.copy(useManualOverride = enabled) }
    }

    fun onManualTargetChanged(target: String) {
        _uiState.update { it.copy(manualTargetInput = target) }
    }

    fun completeOnboarding(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        if (!currentState.isValid || currentState.isSaving) return

        val weight = currentState.weightKg ?: return
        val bottleSize = currentState.bottleSizeMl ?: return

        _uiState.update { it.copy(isSaving = true) }

        viewModelScope.launch {
            try {
                settingsRepository.saveOnboardingSettings(
                    weightKg = weight,
                    bottleSizeMl = bottleSize,
                    manualTargetMl = if (currentState.useManualOverride) currentState.manualTargetMl else null,
                    useManualOverride = currentState.useManualOverride
                )
                onSuccess()
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
