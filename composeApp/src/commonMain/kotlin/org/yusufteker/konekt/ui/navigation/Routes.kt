package org.yusufteker.konekt.ui.navigation

import kotlinx.serialization.Transient
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed class Routes(
    @Transient val screenName: String = ""
) {
    // Onboarding
    @Serializable
    data object OnboardingGraph : Routes("OnboardingGraph")
    @Serializable
    data object Onboarding : Routes("Onboarding")

    // Main
    @Serializable
    data object MainGraph : Routes("MainGraph")
    @Serializable
    data object Dashboard : Routes("Dashboard")
    @Serializable
    data object TaskScreen : Routes("TaskScreen")
    @Serializable
    data object TaskListScreen : Routes("TaskListScreen")
    @Serializable
    data object NotesScreen : Routes("NotesScreen")
    @Serializable
    data object SettingsScreen : Routes("SettingsScreen")
    @Serializable
    data object ProfileScreen : Routes("ProfileScreen")

    @Serializable
    data object Back: Routes("Back")
}
