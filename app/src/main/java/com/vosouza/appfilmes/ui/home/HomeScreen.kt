package com.vosouza.appfilmes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.ui.common.CustomDialog
import com.vosouza.appfilmes.ui.home.favorites.FavoritesScreen
import com.vosouza.appfilmes.ui.home.movies.MovieListScreen
import com.vosouza.appfilmes.ui.home.state.HomeState
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import com.vosouza.appfilmes.ui.home.viewmodel.HomeViewModel
import com.vosouza.appfilmes.ui.theme.black
import com.vosouza.appfilmes.ui.theme.orange
import com.vosouza.appfilmes.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: (Long) -> Unit,
    logOut: () -> Unit
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.brq_movies), color = Color.White, fontSize = 28.sp) },
            actions = {
                HomeMenu(logOut)
            },
        )
    }, content = { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(black)
        ) {
            HomeTabs(state) { tab ->
                viewModel.selectTab(tab)
            }

            when (state.selectedTab) {
                HomeTabs.ALL_MOVIES -> MovieListScreen(
                    modifier,
                    data = state.moviesResponse,
                    movieList = state.movieList,
                    isLoading = state.isLoading,
                    loadMovies = {
                        viewModel.getAllMovies()
                    },
                    loadMore = { itemIndex ->
                        viewModel.getMoreMovies(itemIndex)
                    },
                    navigateToDetail = navigateToDetails,
                )

                HomeTabs.FAVORITE_MOVIES -> FavoritesScreen(
                    modifier = modifier,
                    listData = state.favoriteList,
                    isLoading = state.isLoading ,
                    navigateToDetails = navigateToDetails,
                    removeItem = { id -> viewModel.confirmRemoveItem(id)},
                    loadMovie = {
                        viewModel.getAllMoviesFromDB()
                    },
                )
            }

        }

        if(state.removeItem){
            CustomDialog(
                onConfirmation = { viewModel.doRemoveItem()},
                onDismissRequest = { viewModel.dismissRemoveItem() },
                dialogTitle = stringResource(R.string.remover_favorito),
                dialogText = stringResource(R.string.tem_certeza_que_quer_remover_esse_item_da_lista_de_favorito),
                icon = Icons.Default.Delete
            )
        }
    })
}

@Composable
private fun HomeMenu(logOut: () -> Unit) {
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
                        contentDescription = stringResource(R.string.exit)
                    )
                    Text(stringResource(R.string.sair), color = Color.White, fontSize = 24.sp)
                }
            },
            onClick = {
                logOut.invoke()
            },
        )
    }
}

@Composable
private fun HomeTabs(
    state: HomeState,
    selectTab: (HomeTabs) -> Unit,
) {
    TabRow(selectedTabIndex = state.selectedTab.ordinal) {
        Tab(selected = state.selectedTab == HomeTabs.ALL_MOVIES, onClick = {
            selectTab(HomeTabs.ALL_MOVIES)
        }) {
            Text(
                "Todos os Filmes",
                modifier = Modifier.padding(16.dp),
                color = if (state.selectedTab == HomeTabs.ALL_MOVIES) orange else white,
                fontWeight = FontWeight.Bold
            )
        }
        Tab(selected = state.selectedTab == HomeTabs.FAVORITE_MOVIES, onClick = {
            selectTab(HomeTabs.FAVORITE_MOVIES)
        }) {
            Text(
                "Favoritos",
                modifier = Modifier.padding(16.dp),
                color = if (state.selectedTab == HomeTabs.FAVORITE_MOVIES) orange else white
            )
        }
    }
}