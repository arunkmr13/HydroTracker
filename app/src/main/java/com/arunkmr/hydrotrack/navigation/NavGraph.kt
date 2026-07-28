package com.arunkmr.hydrotrack.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arunkmr.hydrotrack.HydroTrackApp
import com.arunkmr.hydrotrack.ui.ViewModelFactory
import com.arunkmr.hydrotrack.ui.account.AccountScreen
import com.arunkmr.hydrotrack.ui.history.HistoryScreen
import com.arunkmr.hydrotrack.ui.history.HistoryViewModel
import com.arunkmr.hydrotrack.ui.home.HomeScreen
import com.arunkmr.hydrotrack.ui.home.HomeViewModel
import com.arunkmr.hydrotrack.ui.settings.SettingsScreen
import com.arunkmr.hydrotrack.ui.settings.SettingsViewModel
import com.arunkmr.hydrotrack.ui.symptoms.SymptomCheckInScreen
import com.arunkmr.hydrotrack.ui.symptoms.SymptomViewModel
import com.arunkmr.hydrotrack.ui.theme.HydroTeal

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavItem("home", "Home", Icons.Default.WaterDrop)
    object History : NavItem("history", "History", Icons.Default.DateRange)
    object Symptoms : NavItem("symptoms", "Symptoms", Icons.Default.Healing)
    object Account : NavItem("account", "Account", Icons.Default.AccountCircle)
    object Settings : NavItem("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun MainAppNavigation(
    app: HydroTrackApp,
    navController: NavHostController = rememberNavController()
) {
    val factory = ViewModelFactory(app)
    val items = listOf(NavItem.Home, NavItem.History, NavItem.Symptoms, NavItem.Account, NavItem.Settings)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = HydroTeal,
                            selectedTextColor = HydroTeal,
                            indicatorColor = HydroTeal.copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavItem.Home.route) {
                val homeViewModel: HomeViewModel = viewModel(factory = factory)
                HomeScreen(viewModel = homeViewModel)
            }
            composable(NavItem.History.route) {
                val historyViewModel: HistoryViewModel = viewModel(factory = factory)
                HistoryScreen(viewModel = historyViewModel)
            }
            composable(NavItem.Symptoms.route) {
                val symptomViewModel: SymptomViewModel = viewModel(factory = factory)
                SymptomCheckInScreen(viewModel = symptomViewModel)
            }
            composable(NavItem.Account.route) {
                val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
                AccountScreen(viewModel = settingsViewModel)
            }
            composable(NavItem.Settings.route) {
                val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
                SettingsScreen(viewModel = settingsViewModel)
            }
        }
    }
}
