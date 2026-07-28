package com.arunkmr.hydrotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import com.arunkmr.hydrotrack.navigation.MainAppNavigation
import com.arunkmr.hydrotrack.ui.ViewModelFactory
import com.arunkmr.hydrotrack.ui.onboarding.OnboardingScreen
import com.arunkmr.hydrotrack.ui.onboarding.OnboardingViewModel
import com.arunkmr.hydrotrack.ui.theme.HydroTrackTheme
import com.arunkmr.hydrotrack.ui.theme.HydroTeal
import kotlinx.coroutines.flow.map

sealed interface AppState {
    object Loading : AppState
    object OnboardingRequired : AppState
    data class Content(val settings: UserSettingsEntity) : AppState
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as HydroTrackApp
        val factory = ViewModelFactory(app)
        val settingsRepository = SettingsRepository(app.database.settingsDao())

        setContent {
            val settingsState by settingsRepository.getSettings().collectAsState(initial = null)
            val themeMode = settingsState?.themeMode ?: "SYSTEM"

            HydroTrackTheme(themeMode = themeMode) {
                val appStateFlow = remember {
                    settingsRepository.getSettings().map { settings ->
                        when {
                            settings == null || !settings.onboardingCompleted -> AppState.OnboardingRequired
                            else -> AppState.Content(settings)
                        }
                    }
                }

                val appState by appStateFlow.collectAsState(initial = AppState.Loading)

                Scaffold { innerPadding ->
                    when (val state = appState) {
                        is AppState.Loading -> {
                            Surface(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator(color = HydroTeal)
                                }
                            }
                        }

                        is AppState.OnboardingRequired -> {
                            val onboardingViewModel: OnboardingViewModel = viewModel(factory = factory)
                            OnboardingScreen(
                                viewModel = onboardingViewModel,
                                onOnboardingComplete = {
                                    // Room flow automatically updates AppState to Content
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        is AppState.Content -> {
                            MainAppNavigation(app = app)
                        }
                    }
                }
            }
        }
    }
}
