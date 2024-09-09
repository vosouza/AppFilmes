package com.vosouza.appfilmes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDbModel(
    @PrimaryKey var movieId: Long = 0L,
    var posterPath: String = "",
)