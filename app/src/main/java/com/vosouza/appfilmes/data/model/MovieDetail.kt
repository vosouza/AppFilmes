package com.vosouza.appfilmes.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection = BelongsToCollection(),
    var budget: Long = 0L,
    var genres: List<Genre> = listOf(),
    var homepage: String = "",
    var id: Long = 0L,
    @SerializedName("imdb_id")
    var imdbId: String = "",
    @SerializedName("origin_country")
    var originCountry: List<String> = listOf(),
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @SerializedName("original_title")
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany> = listOf(),
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry> = listOf(),
    @SerializedName("release_date")
    var releaseDate: String = "",
    var revenue: Long = 0L,
    var runtime: Long = 0L,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage> = listOf(),
    var status: String = "",
    var tagline: String = "",
    var title: String = "",
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Long = 0L,
)

data class BelongsToCollection(
    var id: Long = 0L,
    var name: String = "",
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
)

data class Genre(
    var id: Long = 0L,
    var name: String = "",
)

data class ProductionCompany(
    var id: Long = 0L,
    @SerializedName("logo_path")
    var logoPath: String = "",
    var name: String = "",
    @SerializedName("origin_country")
    var originCountry: String = "",
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String = "",
    var name: String = "",
)

data class SpokenLanguage(
    @SerializedName("english_name")
    var englishName: String = "",
    @SerializedName("iso_639_1")
    var iso6391: String = "",
    var name: String = "",
)
