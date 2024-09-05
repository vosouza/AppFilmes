package com.vosouza.appfilmes.data.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    var page: Int = 0,
    var results: List<MovieResponse> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Long = 0L,
    @SerializedName("total_results")
    var totalResults: Long = 0L,
)

