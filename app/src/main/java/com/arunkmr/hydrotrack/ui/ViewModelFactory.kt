package com.arunkmr.hydrotrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arunkmr.hydrotrack.HydroTrackApp
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import com.arunkmr.hydrotrack.data.repository.WaterRepository
import com.arunkmr.hydrotrack.ui.history.HistoryViewModel
import com.arunkmr.hydrotrack.ui.home.HomeViewModel
import com.arunkmr.hydrotrack.ui.onboarding.OnboardingViewModel
import com.arunkmr.hydrotrack.ui.settings.SettingsViewModel
import com.arunkmr.hydrotrack.ui.symptoms.SymptomViewModel

class ViewModelFactory(
    private val app: HydroTrackApp
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = app.database
        val settingsRepository = SettingsRepository(database.settingsDao())
        val waterRepository = WaterRepository(database.waterLogDao(), database.symptomLogDao())

        return when {
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(settingsRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(waterRepository, settingsRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(waterRepository, settingsRepository) as T
            }
            modelClass.isAssignableFrom(SymptomViewModel::class.java) -> {
                SymptomViewModel(waterRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(settingsRepository, waterRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
