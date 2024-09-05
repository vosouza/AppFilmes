package com.vosouza.appfilmes.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.data.model.MovieResponse
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.home.state.HomeState
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: MovieRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
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
                    moviesResponse = addList(_homeState.value.moviesResponse, response.results),
                    isLoading = false,
                    totalPages = response.totalPages,
                    currentPage = response.page
                )
            } catch (e: Exception) {
                print(e)
            }

        }
    }

    fun getMoreMovies(itemIndex: Int = 0) {
        val listSize = _homeState.value.moviesResponse.size - 1
        val totalPages = _homeState.value.totalPages
        val isLoading = _homeState.value.isLoading

        if (!isLoading && itemIndex in listSize..<totalPages) {
            getAllMovies(_homeState.value.currentPage + 1)
        }
    }

    private fun addList(firstList: List<MovieResponse>, secondList: List<MovieResponse>) =
        mutableListOf<MovieResponse>().apply {
            addAll(firstList)
            addAll(secondList)
        }.toList()
}
