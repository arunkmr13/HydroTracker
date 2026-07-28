package com.arunkmr.hydrotrack.ui.home

import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import com.arunkmr.hydrotrack.domain.model.DailyProgress

/**
 * UI State for the Home screen.
 */
data class HomeUiState(
    val progress: DailyProgress = DailyProgress(),
    val bottleSizeMl: Int = 850,
    val todayLogs: List<WaterLogEntity> = emptyList(),
    val userName: String? = null,
    val userEmail: String? = null,
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = true
) {
    val encouragingMessage: String
        get() {
            return when {
                progress.isGoalMet -> "Goal reached! Great job staying hydrated 💧"
                progress.progressFraction >= 0.75f -> "Almost there — one last refill will do it!"
                progress.progressFraction >= 0.5f -> "Halfway through your daily goal!"
                progress.currentMl > 0 -> "Still time for one more — you\'ve got this!"
                else -> "Start your day with a fresh bottle of water!"
            }
        }
}
