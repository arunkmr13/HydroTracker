package com.arunkmr.hydrotrack.domain.model

/**
 * Represents the user's water intake progress for a single day.
 */
data class DailyProgress(
    val currentMl: Int = 0,
    val targetMl: Int = 2450,
    val bottleSizeMl: Int = 850,
    val bottleCount: Int = 0,
    val progressFraction: Float = 0f
) {
    val remainingMl: Int
        get() = (targetMl - currentMl).coerceAtLeast(0)

    val isGoalMet: Boolean
        get() = currentMl >= targetMl

    val remainingBottles: Float
        get() = if (bottleSizeMl > 0) remainingMl.toFloat() / bottleSizeMl else 0f

    companion object {
        fun from(
            currentMl: Int,
            targetMl: Int,
            bottleSizeMl: Int
        ): DailyProgress {
            val safeTarget = targetMl.coerceAtLeast(1)
            return DailyProgress(
                currentMl = currentMl,
                targetMl = targetMl,
                bottleSizeMl = bottleSizeMl,
                bottleCount = if (bottleSizeMl > 0) currentMl / bottleSizeMl else 0,
                progressFraction = (currentMl.toFloat() / safeTarget).coerceIn(0f, 1f)
            )
        }
    }
}
