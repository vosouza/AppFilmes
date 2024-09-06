package com.vosouza.appfilmes.ui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.DETAILS_LIST_ROUTE
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.DETAILS_LIST_ROUTE_PARAM
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.HOME_LIST_ROUTE
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.LOGIN_ROUTE

object MovieDestinations {
    const val HOME_LIST_ROUTE = "home"
    const val DETAILS_LIST_ROUTE_PARAM = "movieId"
    const val DETAILS_LIST_ROUTE = "details/{$DETAILS_LIST_ROUTE_PARAM}"
    const val LOGIN_ROUTE = "login"
}

class MovieNavigationActions(navController: NavController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(HOME_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }

    val navigateToDetails: (movieId: Long) -> Unit = { movieId ->
        navController.navigate(DETAILS_LIST_ROUTE.replace("{$DETAILS_LIST_ROUTE_PARAM}",movieId.toString())) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(LOGIN_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}