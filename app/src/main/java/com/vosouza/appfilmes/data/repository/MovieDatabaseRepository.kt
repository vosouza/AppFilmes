package com.vosouza.appfilmes.data.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vosouza.appfilmes.data.model.MovieDbModel

interface MovieDatabaseRepository {

    suspend fun getAllMovies(): List<MovieDbModel>

    suspend fun saveMovie(movie: MovieDbModel)

    suspend fun removeId(id: Long)
}