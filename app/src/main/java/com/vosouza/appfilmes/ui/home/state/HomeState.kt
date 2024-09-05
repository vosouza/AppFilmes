package com.vosouza.appfilmes.ui.home.state

import com.vosouza.appfilmes.data.model.MovieResponse
import com.vosouza.appfilmes.data.model.PopularMoviesResponse

enum class HomeTabs{
    ALL_MOVIES,
    FAVORITE_MOVIES
}

data class HomeState(
    val selectedTab: HomeTabs = HomeTabs.ALL_MOVIES,
    val isLoading: Boolean = false,
    val totalPages: Long = 0L,
    val currentPage: Int = 0,
    val moviesResponse: List<MovieResponse> = listOf()
)