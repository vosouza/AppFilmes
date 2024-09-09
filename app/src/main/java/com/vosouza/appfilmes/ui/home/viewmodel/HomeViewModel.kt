package com.vosouza.appfilmes.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.di.IoDispatcher
import com.vosouza.appfilmes.data.model.MovieResponse
import com.vosouza.appfilmes.data.repository.MovieDatabaseRepository
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.home.state.HomeState
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: MovieRepository,
    val databaseRepository: MovieDatabaseRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _homeState.value
    )

    fun selectTab(selectedTab: HomeTabs) {
        _homeState.value = _homeState.value.copy(
            selectedTab = selectedTab
        )
    }

    fun getAllMovies(itemPage: Int = 1) {
        viewModelScope.launch {

            try {

                _homeState.value = _homeState.value.copy(
                    isLoading = true
                )

                val response = withContext(dispatcher) {
                    repository.getPopularMovies(
                        "pt", itemPage
                    )
                }

                _homeState.value = _homeState.value.copy(
                    moviesResponse = ResultStatus.Success(Unit),
                    movieList = addList(_homeState.value.movieList, response.results),
                    isLoading = false,
                    totalPages = response.totalPages,
                    currentPage = response.page
                )
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    moviesResponse = ResultStatus.Error(e),
                    isLoading = false,
                )
            }

        }
    }

    fun getMoreMovies(itemIndex: Int = 0) {
        val listSize = _homeState.value.movieList.size - 1
        val totalPages = _homeState.value.totalPages
        val isLoading = _homeState.value.isLoading

        if (!isLoading && itemIndex in listSize..<totalPages) {
            getAllMovies(_homeState.value.currentPage + 1)
        }
    }

    fun getAllMoviesFromDB() {
        viewModelScope.launch {

            try {

                _homeState.value = _homeState.value.copy(
                    isLoading = true
                )

                val response = withContext(dispatcher) {
                    databaseRepository.getAllMovies()
                }

                _homeState.value = _homeState.value.copy(
                    favoriteList = response,
                    isLoading = false
                )
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    moviesResponse = ResultStatus.Error(e),
                    isLoading = false,
                )
            }

        }
    }

    fun confirmRemoveItem(movieId: Long) {
        _homeState.value = _homeState.value.copy(
            removeItem = true,
            removeItemId = movieId
        )
    }

    fun dismissRemoveItem() {
        _homeState.value = _homeState.value.copy(
            removeItem = false,
            removeItemId = 0L
        )
    }

    fun doRemoveItem() {
        viewModelScope.launch {
            try {

                val id = _homeState.value.removeItemId

                withContext(dispatcher) {
                    databaseRepository.removeId(id)
                }

                dismissRemoveItem()
                getAllMoviesFromDB()
            } catch (e: Exception) {
                dismissRemoveItem()
            }
        }
    }

    private fun addList(firstList: List<MovieResponse>, secondList: List<MovieResponse>) =
        mutableListOf<MovieResponse>().apply {
            addAll(firstList)
            addAll(secondList)
        }.toList()
}
