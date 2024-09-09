package com.vosouza.appfilmes.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vosouza.appfilmes.R
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.core.util.imageNetworkURL
import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.ui.details.viewmodel.DetailsState
import com.vosouza.appfilmes.ui.details.viewmodel.DetailsViewModel
import com.vosouza.appfilmes.ui.theme.gray
import com.vosouza.appfilmes.ui.theme.orange
import com.vosouza.appfilmes.ui.theme.white

@Composable
fun DetailsScreen(
    movieId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {

    val state = viewModel.detailState.collectAsStateWithLifecycle()
    val saveState = viewModel.saveMovieState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    var showTopAppBar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(scrollState.value) {
        if (scrollState.value > 0) {
            showTopAppBar = true
        } else if (scrollState.value == 0) {
            showTopAppBar = false
        }
    }

    SaveMovieSnackBar(saveState, snackbarHostState)

    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
    }

    when (state.value) {
        is ResultStatus.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(124.dp)
                        .padding(24.dp)
                )
            }
        }

        is ResultStatus.Success -> {
            val data = (state.value as ResultStatus.Success<DetailsState>).data.movie
            SuccessScreen(showTopAppBar, scrollState, data, snackbarHostState, navigateBack) {
                viewModel.saveMovie(data.id, data.posterPath)
            }
        }

        is ResultStatus.Error -> {
            val text = (state.value as ResultStatus.Error).throwable.message
                ?: stringResource(R.string.ocorreu_um_erro_tente_novamente_mais_tarde)

            Row(
                Modifier.padding(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = text, fontSize = 24.sp, color = orange)
            }
        }
    }
}

@Composable
private fun SaveMovieSnackBar(
    saveState: State<ResultStatus<Any>>,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(key1 = saveState.value) {
        when (saveState.value) {
            is ResultStatus.Loading -> Unit
            is ResultStatus.Success -> {
                snackbarHostState.showSnackbar("Filme Salvo")
            }

            is ResultStatus.Error -> {
                snackbarHostState.showSnackbar("Erro ao salvar, por favor tente mais tarde")
            }
        }
    }
}

@Composable
private fun SuccessScreen(
    showTopAppBar: Boolean,
    scrollState: ScrollState,
    movie: MovieDetailResponse,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit,
    saveMovie: () -> Unit,
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = showTopAppBar,
                enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(),
                exit = slideOutVertically() + shrinkVertically()
            ) {
                DetailsAppBar(movie.title, navigateBack, saveMovie)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color(0xFF121212))
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterPath.imageNetworkURL())
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED).build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop
                )
                if (!showTopAppBar) {
                    IconButton(
                        onClick = { navigateBack.invoke() },
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.arrow_icon_white),
                            contentDescription = "Voltar",
                            tint = Color.Unspecified,
                        )
                    }
                    IconButton(
                        onClick = { saveMovie.invoke() },
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.like_icon_white),
                            contentDescription = "Voltar",
                            tint = Color.Unspecified,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.sinopse),
                color = orange,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                color = white,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonWithLabel(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 16.dp, bottom = 8.dp, top = 16.dp, end = 8.dp),
                    icon = R.drawable.like_icon_black,
                    stringResource(R.string.popularidade),
                    movie.popularity.toString()
                )
                ButtonWithLabel(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 8.dp, bottom = 8.dp, top = 16.dp, end = 16.dp),
                    icon = R.drawable.star_icon,
                    stringResource(R.string.votos),
                    movie.voteCount.toString()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonWithLabel(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 16.dp, bottom = 8.dp, top = 16.dp, end = 8.dp),
                    icon = R.drawable.calendar_icon,
                    stringResource(R.string.lan_amento),
                    movie.releaseDate
                )
                ButtonWithLabel(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 8.dp, bottom = 8.dp, top = 16.dp, end = 16.dp),
                    icon = R.drawable.bell_icon,
                    stringResource(R.string.status),
                    movie.status
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsAppBar(
    title: String,
    navigateBack: () -> Unit,
    saveMovie: () -> Unit,
) {
    TopAppBar(navigationIcon = {
        IconButton(
            onClick = { navigateBack.invoke() }, modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.arrow_icon),
                contentDescription = stringResource(R.string.voltar),
                tint = Color.Unspecified,
            )
        }
    }, actions = {
        IconButton(
            onClick = { },
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                onClick = { saveMovie.invoke() }, modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.like_icon),
                    contentDescription = stringResource(R.string.favoritar),
                    tint = Color.Unspecified,
                )
            }
        }
    }, title = {
        Text(text = title, fontSize = 24.sp)
    })
}

@Composable
fun ButtonWithLabel(modifier: Modifier, icon: Int, label: String, text: String) {
    Card(
        modifier = modifier, colors = CardColors(
            containerColor = gray,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = stringResource(R.string.icon)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = orange,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                color = white, text = text, fontSize = 24.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    DetailsScreen("0") {}
}