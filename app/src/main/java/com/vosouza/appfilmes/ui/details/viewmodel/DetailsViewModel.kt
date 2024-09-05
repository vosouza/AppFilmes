package com.vosouza.appfilmes.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.di.IoDispatcher
import com.vosouza.appfilmes.data.model.MovieDetailResponse
import com.vosouza.appfilmes.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _homeState: MutableStateFlow<ResultStatus<DetailsState>> =
        MutableStateFlow(ResultStatus.Loading)
    val detailState = _homeState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _homeState.value
    )

    fun getMovie(movieId: String) {
        viewModelScope.launch {

            try {

                _homeState.value = ResultStatus.Loading

                val id = movieId.toInt()

                val response = withContext(dispatcher) {
                    repository.getMovieDetail(id)
                }

                _homeState.value = ResultStatus.Success(
                    DetailsState(
                        response
                    )
                )

            } catch (e: Exception) {
                _homeState.value = ResultStatus.Error(e)
            }

        }
    }

}

data class DetailsState(
    val movie: MovieDetailResponse,
)