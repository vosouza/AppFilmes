package com.vosouza.appfilmes.data.di

import com.vosouza.appfilmes.data.repository.LoginRepository
import com.vosouza.appfilmes.data.repository.MovieRepository
import com.vosouza.appfilmes.data.repository.implementation.FakeLoginRepositoryImpl
import com.vosouza.appfilmes.data.repository.implementation.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMovieRepository(repositoryImpl: MoviesRepositoryImpl): MovieRepository

    @Binds
    fun bindLoginRepository(loginRepository: FakeLoginRepositoryImpl): LoginRepository

}