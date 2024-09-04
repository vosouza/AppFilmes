package com.vosouza.appfilmes.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MoviesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navDestinations: MovieNavigationActions,
    startDestination: String = MovieDestinations.MOVIE_LIST_ROUTE,
){
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable(
            route = MovieDestinations.MOVIE_LIST_ROUTE
        ){

        }
        composable(
            route = MovieDestinations.FAVORITE_LIST_ROUTE
        ){ navBackStackEntry ->

        }

    }
}