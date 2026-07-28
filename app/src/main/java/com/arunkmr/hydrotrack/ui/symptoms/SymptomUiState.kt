package com.arunkmr.hydrotrack.ui.symptoms

import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity

data class SymptomUiState(
    val dateEpochDay: Long = 0,
    val feltDizzy: Boolean = false,
    val drySkin: Boolean = false,
    val notes: String = "",
    val isSaved: Boolean = false,
    val isLoading: Boolean = true,
    val pastSymptoms: List<SymptomLogEntity> = emptyList()
)
