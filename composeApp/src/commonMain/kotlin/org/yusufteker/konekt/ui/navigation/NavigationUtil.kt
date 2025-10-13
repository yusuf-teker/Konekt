package org.yusufteker.konekt.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

fun isOnCurrentDestination(destinationRoute: String, currentDestination: NavDestination?): Boolean {
    return  currentDestination
        ?.hierarchy
        ?.any {
            it.route?.contains(destinationRoute) ?: false
        } == true

}

fun NavHostController.navigateTo(navigationModel: NavigationModel) {
    when {
        navigationModel.isBack -> {
            popBackStack()
            return
        }

        navigationModel.popUpToRoute != null -> {
            navigate(navigationModel.route) {
                popUpTo(navigationModel.popUpToRoute) {
                    inclusive = navigationModel.inclusive
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        else -> {
            navigate(navigationModel.route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}
