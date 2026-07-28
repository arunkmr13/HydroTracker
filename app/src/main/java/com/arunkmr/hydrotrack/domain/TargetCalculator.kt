package com.arunkmr.hydrotrack.domain

import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity
import kotlin.math.roundToInt

/**
 * Pure function to calculate the daily water intake target.
 *
 * Logic:
 * - If the user has enabled manual override and provided a valid manual target (> 0), use that.
 * - Otherwise, calculate target as weightKg × 35 ml/kg (standard medical recommendation).
 */
object TargetCalculator {

    fun calculateTarget(settings: UserSettingsEntity): Int {
        return if (settings.useManualOverride && settings.manualTargetMl != null && settings.manualTargetMl > 0) {
            settings.manualTargetMl
        } else {
            (settings.weightKg * 35).roundToInt()
        }
    }

    /**
     * Calculate the number of bottles needed to reach the target.
     */
    fun bottlesForTarget(settings: UserSettingsEntity): Float {
        val target = calculateTarget(settings)
        return target.toFloat() / settings.bottleSizeMl
    }
}
