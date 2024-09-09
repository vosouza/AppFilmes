package com.vosouza.appfilmes.ui.home.state

import com.vosouza.appfilmes.core.util.ResultStatus
import com.vosouza.appfilmes.data.model.MovieDbModel
import com.vosouza.appfilmes.data.model.MovieResponse

enum class HomeTabs{
    ALL_MOVIES,
    FAVORITE_MOVIES
}

data class HomeState(
    val selectedTab: HomeTabs = HomeTabs.ALL_MOVIES,
    val totalPages: Long = 0L,
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val moviesResponse: ResultStatus<Unit> = ResultStatus.Loading,
    val movieList: List<MovieResponse> = listOf(),
    val favoriteList: List<MovieDbModel> = listOf(),
    val removeItem: Boolean = false,
    val removeItemId: Long = 0,
)