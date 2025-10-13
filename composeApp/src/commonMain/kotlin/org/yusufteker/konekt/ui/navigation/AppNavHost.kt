package org.yusufteker.konekt.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.ui.screen.dashboard.presentation.DashboardScreenRoot
import org.yusufteker.konekt.ui.screen.notes.presentation.NotesScreenRoot
import org.yusufteker.konekt.ui.screen.settings.presentation.SettingsScreenRoot
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListScreenRoot
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    //onboardingManager: OnboardingManager = koinInject()
) {
    var showBottomBar by remember { mutableStateOf(false) }

    val onboardingDone = true // Todo yusuf managera al
    if (onboardingDone == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (onboardingDone) Routes.MainGraph else Routes.OnboardingGraph

    Scaffold(
        bottomBar = {
            // TODO: İleride BottomNavigationBar ekle
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {

            // Onboarding Graph
            navigation<Routes.OnboardingGraph>(startDestination = Routes.Onboarding) {
                composable<Routes.Onboarding> {
                    // TODO: OnboardingScreenRoot()
                }
            }

            // Main Graph
            navigation<Routes.MainGraph>(startDestination = Routes.Dashboard) {
                composable<Routes.Dashboard> {
                    DashboardScreenRoot(
                        onNavigateTo = { navModel ->
                            navController.navigateTo(navModel)
                        }
                    )
                }
                composable<Routes.TaskListScreen> {
                    TaskListScreenRoot(
                        onNavigateTo = { navModel ->
                            navController.navigateTo(navModel)
                        }
                    )
                }
                composable<Routes.NotesScreen> {
                    NotesScreenRoot(
                        onNavigateTo = { navModel ->
                            navController.navigateTo(navModel)
                        }
                    )
                }
                composable<Routes.SettingsScreen> {
                    SettingsScreenRoot {
                        navController.navigateTo(it)
                    }
                }
                composable<Routes.ProfileScreen> {
                    // TODO: ProfileScreenRoot()
                }
            }
        }
    }
}
