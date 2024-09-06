package com.vosouza.appfilmes.ui.home.movies

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.core.util.imageNetworkURL
import com.vosouza.appfilmes.data.model.MovieResponse

@Composable
fun MovieListScreen(
    modifier: Modifier,
    data: ResultStatus<Unit>,
    loadMore: (Int) -> Unit,
    isLoading: Boolean,
    navigateToDetail: (Long) -> Unit,
    movieList: List<MovieResponse>,
) {
    when (data) {
        is ResultStatus.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }

        is ResultStatus.Success -> {

            MoviesList(loadMore, navigateToDetail, isLoading, movieList ,modifier)
        }

        is ResultStatus.Error -> {
            Text(text = "Ocorreu um erro, tente novamente mais tarde")
        }
    }

}

@Composable
private fun MoviesList(
    loadMore: (Int) -> Unit,
    navigateToDetail: (Long) -> Unit,
    isLoading: Boolean,
    listData: List<MovieResponse>,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(listData.size) { index ->
            loadMore(index)
            MoviePoster(
                navigateToDetail,
                listData[index]
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

@Composable
fun MoviePoster(navigateToDetail: (Long) -> Unit, movie: MovieResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { navigateToDetail.invoke(movie.id) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterPath.imageNetworkURL())
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
    }
}