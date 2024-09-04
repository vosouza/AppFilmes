package com.vosouza.appfilmes.ui.home.state

enum class HomeTabs{
    ALL_MOVIES,
    FAVORITE_MOVIES
}

data class HomeState(
    val selectedTab: HomeTabs = HomeTabs.ALL_MOVIES,
)