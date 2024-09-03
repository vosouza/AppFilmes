package com.vosouza.appfilmes.data.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    var page: Int,
    var results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
)

