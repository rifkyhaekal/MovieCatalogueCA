package com.albro.alfamoviecatalogue.core.data.source.local

import com.albro.alfamoviecatalogue.core.data.source.local.entity.MovieEntity
import com.albro.alfamoviecatalogue.core.data.source.local.entity.TvShowEntity
import com.albro.alfamoviecatalogue.core.data.source.local.room.MovieDao
import com.albro.alfamoviecatalogue.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val mMovieDao: MovieDao) {

    fun getAllMovies(query: String): Flow<List<MovieEntity>> =
        mMovieDao.getMovies(SortUtils.getSortedMovieQuery(query))

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = mMovieDao.getFavoriteMovies()

    fun getMovieDetail(movieId: Int): Flow<MovieEntity> = mMovieDao.getMovieDetail(movieId)

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieDao.updateMovie(movie)
    }

    suspend fun updateMovieDetail(
        movieId: Int,
        genre: String,
        overview: String,
        duration: String,
        userScore: Float,
        releaseDate: String,
        status: String,
        backdropPath: String
    ) {
        mMovieDao.updateMovieDetail(
            movieId,
            genre,
            overview,
            duration,
            userScore,
            releaseDate,
            status,
            backdropPath
        )
    }

    fun getSearchMovies(title: String): Flow<List<MovieEntity>> {
        return mMovieDao.getSearchMovies(title)
    }

    fun getAllTvShows(query: String): Flow<List<TvShowEntity>> =
        mMovieDao.getTvShows(SortUtils.getSortedTvShowQuery(query))

    fun getFavoriteTvShows(): Flow<List<TvShowEntity>> = mMovieDao.getFavoriteTvShows()

    fun getTvShowDetail(tvShowId: Int): Flow<TvShowEntity> = mMovieDao.getTvShowDetail(tvShowId)

    suspend fun insertTvShow(tvShows: List<TvShowEntity>) = mMovieDao.insertTvShows(tvShows)

    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorite = newState
        mMovieDao.updateTvShow(tvShow)
    }

    suspend fun updateTvShowDetail(
        tvShowId: Int,
        overview: String,
        status: String,
        type: String,
        genre: String,
        network: String,
        userScore: Float,
        backdropPath: String
    ) {
        mMovieDao.updateTvShowDetail(
            tvShowId,
            overview,
            status,
            type,
            genre,
            network,
            userScore,
            backdropPath
        )
    }

    fun getSearchTvShows(title: String): Flow<List<TvShowEntity>> {
        return mMovieDao.getSearchTvShows(title)
    }
}