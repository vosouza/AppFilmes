package com.vosouza.appfilmes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.ui.home.favorites.FavoritesScreen
import com.vosouza.appfilmes.ui.home.movies.MovieListScreen
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import com.vosouza.appfilmes.ui.home.viewmodel.HomeViewModel
import com.vosouza.appfilmes.ui.theme.black
import com.vosouza.appfilmes.ui.theme.orange
import com.vosouza.appfilmes.ui.theme.white
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
){
    val state by viewModel.homeState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.getAllMovies()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("BRQ Movies", color = Color.White, fontSize = 28.sp) },
            actions = {
                var menuExpanded by remember {
                    mutableStateOf(false)
                }

                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.menu_icon),
                        contentDescription = "Menu",
                        tint = Color.Unspecified
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.exit_icon),
                                    contentDescription = "Exit"
                                )
                                Text("Sair", color = Color.White, fontSize = 24.sp)
                            }
                        },
                        onClick = {

                        },
                    )
                }
            },
        )
    }, content = { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(black)
        ) {
            TabRow(selectedTabIndex = state.selectedTab.ordinal) {
                Tab(selected = state.selectedTab == HomeTabs.ALL_MOVIES, onClick = {
                    viewModel.selectTab(HomeTabs.ALL_MOVIES)
                }) {
                    Text(
                        "Todos os Filmes",
                        modifier = Modifier.padding(16.dp),
                        color = if (state.selectedTab == HomeTabs.ALL_MOVIES) orange else white,
                        fontWeight = FontWeight.Bold
                    )
                }
                Tab(selected = state.selectedTab == HomeTabs.FAVORITE_MOVIES, onClick = {
                    viewModel.selectTab(HomeTabs.FAVORITE_MOVIES)
                }) {
                    Text(
                        "Outra Categoria",
                        modifier = Modifier.padding(16.dp),
                        color =  if (state.selectedTab == HomeTabs.FAVORITE_MOVIES) orange else white
                    )
                }
            }

            when(state.selectedTab){
                HomeTabs.ALL_MOVIES -> MovieListScreen(modifier)
                HomeTabs.FAVORITE_MOVIES -> FavoritesScreen()
            }

        }
    })
}