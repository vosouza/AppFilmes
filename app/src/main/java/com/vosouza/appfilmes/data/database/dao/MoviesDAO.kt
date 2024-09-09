package com.vosouza.appfilmes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vosouza.appfilmes.data.model.MovieDbModel

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM MovieDbModel")
    fun getAllMovies(): List<MovieDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: MovieDbModel)

    @Query("DELETE FROM MovieDbModel WHERE MovieDbModel.movieId = :id")
    fun removeById(id: Long)
}