package com.vosouza.appfilmes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vosouza.appfilmes.data.database.dao.MoviesDAO
import com.vosouza.appfilmes.data.model.MovieDbModel

@Database(entities = [MovieDbModel::class], version = 1)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieDAO(): MoviesDAO
}