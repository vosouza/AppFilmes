package com.vosouza.appfilmes.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.vosouza.appfilmes.ui.main.navigation.MovieNavigationActions
import com.vosouza.appfilmes.ui.main.navigation.MoviesNavGraph
import com.vosouza.appfilmes.ui.theme.AppFilmesTheme

@Composable
fun MovieApp() {
    AppFilmesTheme {

        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            MovieNavigationActions(navController)
        }

        MoviesNavGraph(
            modifier = Modifier,
            navController,
            navigationActions,
        )
    }
}


@Composable
@Preview
fun Preview() {
    MovieApp()
}