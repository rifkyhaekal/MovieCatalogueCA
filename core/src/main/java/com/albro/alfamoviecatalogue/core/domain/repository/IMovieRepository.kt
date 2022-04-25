package com.albro.alfamoviecatalogue.core.domain.repository

import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(sort: String): Flow<Resource<List<Movie>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getSearchMovies(title: String): Flow<List<Movie>>

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun getTvShows(sort: String): Flow<Resource<List<TvShow>>>

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShow>>

    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun getSearchTvShows(title: String): Flow<List<TvShow>>

    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)
}