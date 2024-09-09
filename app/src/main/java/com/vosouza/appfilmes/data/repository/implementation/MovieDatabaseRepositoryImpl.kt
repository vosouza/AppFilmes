package com.vosouza.appfilmes.data.repository.implementation

import com.vosouza.appfilmes.data.database.dao.MoviesDAO
import com.vosouza.appfilmes.data.model.MovieDbModel
import com.vosouza.appfilmes.data.repository.MovieDatabaseRepository
import javax.inject.Inject

class MovieDatabaseRepositoryImpl @Inject constructor(
    private val dao: MoviesDAO,
) : MovieDatabaseRepository {

    override suspend fun getAllMovies(): List<MovieDbModel> {
        return dao.getAllMovies()
    }

    override suspend fun saveMovie(movie: MovieDbModel) {
        return dao.saveMovie(movie)
    }

    override suspend fun removeId(id: Long) {
        return dao.removeById(id)
    }

}