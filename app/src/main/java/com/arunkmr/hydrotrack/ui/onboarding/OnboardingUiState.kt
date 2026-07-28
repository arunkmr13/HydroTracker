package com.arunkmr.hydrotrack.ui.onboarding

import kotlin.math.roundToInt

/**
 * UI State for the Onboarding screen.
 */
data class OnboardingUiState(
    val weightInput: String = "70",
    val bottleSizeInput: String = "850",
    val useManualOverride: Boolean = false,
    val manualTargetInput: String = "",
    val isSaving: Boolean = false
) {
    val weightKg: Float?
        get() = weightInput.toFloatOrNull()?.takeIf { it > 0 }

    val bottleSizeMl: Int?
        get() = bottleSizeInput.toIntOrNull()?.takeIf { it > 0 }

    val manualTargetMl: Int?
        get() = manualTargetInput.toIntOrNull()?.takeIf { it > 0 }

    val calculatedTargetMl: Int
        get() {
            val weight = weightKg ?: 70f
            return (weight * 35).roundToInt()
        }

    val effectiveTargetMl: Int
        get() {
            return if (useManualOverride && manualTargetMl != null) {
                manualTargetMl!!
            } else {
                calculatedTargetMl
            }
        }

    val bottleRefillEstimate: Float
        get() {
            val size = bottleSizeMl ?: 850
            return effectiveTargetMl.toFloat() / size
        }

    val isValid: Boolean
        get() {
            val isWeightValid = weightKg != null
            val isBottleValid = bottleSizeMl != null
            val isManualValid = !useManualOverride || manualTargetMl != null
            return isWeightValid && isBottleValid && isManualValid
        }
}
