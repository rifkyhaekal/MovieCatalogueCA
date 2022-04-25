package com.albro.alfamoviecatalogue.core.domain.usecase

import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val iMovieRepository: IMovieRepository) : MovieUseCase {
    override fun getMovies(sort: String): Flow<Resource<List<Movie>>> =
        iMovieRepository.getMovies(sort)

    override fun getMovieDetail(movieId: Int): Flow<Resource<Movie>> =
        iMovieRepository.getMovieDetail(movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> = iMovieRepository.getFavoriteMovies()

    override fun getSearchMovies(title: String): Flow<List<Movie>> =
        iMovieRepository.getSearchMovies(title)

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        iMovieRepository.setMovieFavorite(movie, state)

    override fun getTvShows(sort: String): Flow<Resource<List<TvShow>>> =
        iMovieRepository.getTvShows(sort)

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShow>> =
        iMovieRepository.getTvShowDetail(tvShowId)

    override fun getFavoriteTvShow(): Flow<List<TvShow>> = iMovieRepository.getFavoriteTvShows()

    override fun getSearchTvShows(title: String): Flow<List<TvShow>> =
        iMovieRepository.getSearchTvShows(title)

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) =
        iMovieRepository.setTvShowFavorite(tvShow, state)
}