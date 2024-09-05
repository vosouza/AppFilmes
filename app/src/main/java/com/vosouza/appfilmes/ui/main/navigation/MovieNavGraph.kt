package com.vosouza.appfilmes.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vosouza.appfilmes.ui.details.DetailsScreen
import com.vosouza.appfilmes.ui.home.HomeScreen
import com.vosouza.appfilmes.ui.login.LoginScreen


@Composable
fun MoviesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navDestinations: MovieNavigationActions,
    startDestination: String = MovieDestinations.HOME_LIST_ROUTE,
){
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable(
            route = MovieDestinations.HOME_LIST_ROUTE
        ){
            HomeScreen(modifier = modifier){ movieId ->
                navDestinations.navigateToDetails.invoke(movieId)
            }
        }
        composable(
            route = MovieDestinations.DETAILS_LIST_ROUTE
        ){ navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString(MovieDestinations.DETAILS_LIST_ROUTE_PARAM) ?: ""

            DetailsScreen(movieId = movieId){
                navDestinations.navigateToHome.invoke()
            }
        }
        composable(
            route = MovieDestinations.LOGIN_ROUTE
        ){ navBackStackEntry ->
            LoginScreen(modifier = modifier, navigateToHome = navDestinations.navigateToHome)
        }

    }
}