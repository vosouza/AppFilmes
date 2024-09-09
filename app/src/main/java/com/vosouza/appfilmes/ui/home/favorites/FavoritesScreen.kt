package com.vosouza.appfilmes.ui.home.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.core.util.imageNetworkURL
import com.vosouza.appfilmes.data.model.MovieDbModel

@Composable
fun FavoritesScreen(
    modifier: Modifier,
    listData: List<MovieDbModel>,
    isLoading: Boolean,
    loadMovie: () -> Unit,
    navigateToDetails: (Long) -> Unit,
    removeItem: (Long) -> Unit,
) {

    LaunchedEffect(Unit) {
        loadMovie.invoke()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listData.size) { index ->
            MoviePoster(
                listData[index], navigateToDetails, removeItem
            )
        }

        if (isLoading) {
            item {
                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviePoster(
    movie: MovieDbModel,
    navigateToDetails: (Long) -> Unit,
    removeItem: (Long) -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .combinedClickable(onClick = { navigateToDetails.invoke(movie.movieId) }, onLongClick = {
            removeItem.invoke(movie.movieId)
        }), shape = MaterialTheme.shapes.medium, elevation = CardDefaults.cardElevation(4.dp)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterPath.imageNetworkURL()).networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED).build(),
            contentDescription = stringResource(R.string.saved_movie),
            contentScale = ContentScale.Crop
        )
    }
}
