package com.vosouza.appfilmes.ui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.MOVIE_LIST_ROUTE
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.FAVORITE_LIST_ROUTE
import com.vosouza.appfilmes.ui.main.navigation.MovieDestinations.LOGIN_ROUTE

object MovieDestinations {
    const val MOVIE_LIST_ROUTE = "input"
    const val FAVORITE_LIST_ROUTE = "input"
    const val LOGIN_ROUTE = "login"
}

class MovieNavigationActions(navController: NavController){

    val navigateToMovieList: () -> Unit = {
        navController.navigate(MOVIE_LIST_ROUTE){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToFavorite: () -> Unit = {
        navController.navigate(FAVORITE_LIST_ROUTE){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(LOGIN_ROUTE){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}