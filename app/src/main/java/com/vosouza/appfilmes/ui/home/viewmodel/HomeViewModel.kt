package com.vosouza.appfilmes.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.ui.home.state.HomeState
import com.vosouza.appfilmes.ui.home.state.HomeTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: MovieRepository
): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _homeState.value
    )

    fun selectTab(selectedTab: HomeTabs){
        _homeState.value = _homeState.value.copy(
            selectedTab = selectedTab
        )
    }

    fun getAllMovies(){
        viewModelScope.launch {

            try {

                val response = withContext(Dispatchers.IO){
                    repository.getPopularMovies(
                        "pt",
                        1
                    )
                }
                response
            }catch (e: Exception){
                print(e)
            }

        }
    }

}