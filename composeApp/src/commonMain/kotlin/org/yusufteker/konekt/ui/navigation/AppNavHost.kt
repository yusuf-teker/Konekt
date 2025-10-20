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
import org.yusufteker.konekt.data.repository.SessionManagerImpl
import org.yusufteker.konekt.domain.repository.AuthRepository
import org.yusufteker.konekt.ui.screen.dashboard.presentation.DashboardScreenRoot
import org.yusufteker.konekt.ui.screen.login.presentation.LoginScreenRoot
import org.yusufteker.konekt.ui.screen.notes.presentation.NotesScreenRoot
import org.yusufteker.konekt.ui.screen.register.presentation.RegisterScreenRoot
import org.yusufteker.konekt.ui.screen.settings.presentation.SettingsScreenRoot
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListScreenRoot
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.yusufteker.konekt.util.DataError

@Composable
fun AppNavHost(
    navController: NavHostController,
    sessionManager: SessionManagerImpl = koinInject(),
    authRepository: AuthRepository = koinInject(),

    //onboardingManager: OnboardingManager = koinInject()
) {


    /*
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
    }*/
    var startDestination by remember { mutableStateOf<Routes?>(null) }
    var checkingAuth by remember { mutableStateOf(true) }

    // Token kontrolü ve doğrulama
    LaunchedEffect(Unit) {
        val token = sessionManager.getToken()
        startDestination = if (token != null) Routes.MainGraph else Routes.AuthGraph

        // Arka planda server doğrulama
        if (token != null) {
            val result = authRepository.getCurrentUser() // token geçerli mi server kontrolü
            if (result is DataError) {
                sessionManager.clearAll()
                startDestination = Routes.AuthGraph
            }
        }
        checkingAuth = false
    }

    if (checkingAuth || startDestination == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }

    if (startDestination == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    Scaffold(
        bottomBar = {
            // TODO: İleride BottomNavigationBar ekle
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = Modifier.padding(innerPadding)
        ) {

            // Onboarding Graph
            navigation<Routes.OnboardingGraph>(startDestination = Routes.Onboarding) {
                composable<Routes.Onboarding> {
                    // TODO: OnboardingScreenRoot()
                }
            }


            navigation<Routes.AuthGraph>(startDestination = Routes.Register){

                composable<Routes.Register> {
                    RegisterScreenRoot (
                        onNavigateTo = { navModel ->
                            navController.navigateTo(navModel)
                        }
                    )
                }
                composable<Routes.Login> {
                    LoginScreenRoot(
                        onNavigateTo = { navModel ->
                            navController.navigateTo(navModel)
                        }
                    )
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
