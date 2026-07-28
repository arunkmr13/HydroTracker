package com.arunkmr.hydrotrack.ui.symptoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import com.arunkmr.hydrotrack.data.repository.WaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SymptomViewModel(
    private val waterRepository: WaterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SymptomUiState())
    val uiState: StateFlow<SymptomUiState> = _uiState.asStateFlow()

    private val todayEpochDay = LocalDate.now().toEpochDay()

    init {
        viewModelScope.launch {
            // Observe today's symptom check-in
            waterRepository.getTodaySymptom().collect { existing ->
                if (existing != null) {
                    _uiState.update {
                        it.copy(
                            dateEpochDay = existing.dateEpochDay,
                            feltDizzy = existing.feltDizzy,
                            drySkin = existing.drySkin,
                            notes = existing.notes ?: "",
                            isSaved = true,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            dateEpochDay = todayEpochDay,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onDizzyToggled(dizzy: Boolean) {
        _uiState.update { it.copy(feltDizzy = dizzy, isSaved = false) }
    }

    fun onDrySkinToggled(drySkin: Boolean) {
        _uiState.update { it.copy(drySkin = drySkin, isSaved = false) }
    }

    fun onNotesChanged(notes: String) {
        _uiState.update { it.copy(notes = notes, isSaved = false) }
    }

    fun saveCheckIn() {
        val state = _uiState.value
        val entity = SymptomLogEntity(
            dateEpochDay = todayEpochDay,
            feltDizzy = state.feltDizzy,
            drySkin = state.drySkin,
            notes = state.notes.takeIf { it.isNotBlank() }
        )

        viewModelScope.launch {
            waterRepository.logSymptom(entity)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
