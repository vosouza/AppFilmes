package com.vosouza.appfilmes.data.di

import android.content.Context
import androidx.room.Room
import com.vosouza.appfilmes.data.database.MoviesDB
import com.vosouza.appfilmes.data.database.dao.MoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "MoviesDBBRQ.db"

    @Singleton
    @Provides
    fun provideMoviesDao(
        database: MoviesDB
    ): MoviesDAO{
        return database.movieDAO()
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MoviesDB{
        return Room.databaseBuilder(
            context.applicationContext,
            MoviesDB::class.java,
            DATABASE_NAME
        ).build()
    }

}