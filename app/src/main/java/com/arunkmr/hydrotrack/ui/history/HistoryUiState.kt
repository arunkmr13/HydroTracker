package com.arunkmr.hydrotrack.ui.history

import com.arunkmr.hydrotrack.data.local.DailyTotal
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity

data class DayHistoryItem(
    val dateEpochDay: Long,
    val dateString: String,
    val totalMl: Int,
    val targetMl: Int,
    val progressFraction: Float,
    val isGoalMet: Boolean,
    val symptom: SymptomLogEntity?
)

data class HistoryUiState(
    val rolling7DayAverageMl: Int = 0,
    val targetMl: Int = 2450,
    val last30Days: List<DayHistoryItem> = emptyList(),
    val isLoading: Boolean = true
)
