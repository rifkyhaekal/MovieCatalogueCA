package com.albro.alfamoviecatalogue.core.data.source

import com.albro.alfamoviecatalogue.core.data.source.local.LocalDataSource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.repository.IMovieRepository
import com.albro.alfamoviecatalogue.core.utils.*
import com.example.haekalmoviecatalogue.data.source.remote.ApiResponse
import com.example.haekalmoviecatalogue.data.source.remote.RemoteDataSource
import com.example.haekalmoviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.haekalmoviecatalogue.data.source.remote.response.MovieResponse
import com.example.haekalmoviecatalogue.data.source.remote.response.TvShowDetailResponse
import com.example.haekalmoviecatalogue.data.source.remote.response.TvShowResponse
import com.example.haekalmoviecatalogue.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getMovies(sort: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, MovieResponse>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: MovieResponse) {
                val movieList = DataMapper.mapMovieResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }

        }.asLiveData()

    override fun getMovieDetail(movieId: Int): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieDetail(movieId).map {
                    DataMapper.mapMovieDetailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean = data?.movieDetail?.genre == null

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movieId)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                localDataSource.updateMovieDetail(
                    movieId = data.id,
                    genre = generateMovieGenres(data.genres),
                    overview = setOverview(data.overview),
                    duration = generateMovieDuration(data.runtime),
                    userScore = setRating(data.voteAverage),
                    releaseDate = data.releaseDate,
                    status = data.status,
                    backdropPath = data.backdropPath
                )
            }

        }.asLiveData()

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    override fun getSearchMovies(title: String): Flow<List<Movie>> =
        localDataSource.getSearchMovies(title).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntities(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

    override fun getTvShows(sort: String): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundResource<List<TvShow>, TvShowResponse>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows(sort).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<TvShowResponse>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: TvShowResponse) {
                val tvShowList = DataMapper.mapTvShowResponseToEntities(data)
                localDataSource.insertTvShow(tvShowList)
            }

        }.asLiveData()

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShow>> =
        object : NetworkBoundResource<TvShow, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvShowDetail(tvShowId).map {
                    DataMapper.mapTvShowDetailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean = data?.tvShowDetail?.genre == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShowId)
            }

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                localDataSource.updateTvShowDetail(
                    tvShowId = data.id,
                    overview = setOverview(data.overview),
                    status = data.status,
                    type = data.type,
                    genre = generateTvShowGenres(data.genres),
                    network = generateNetworks(data.networks),
                    userScore = setRating(data.voteAverage),
                    backdropPath = data.backdropPath
                )
            }

        }.asLiveData()


    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        localDataSource.getFavoriteTvShows().map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }

    override fun getSearchTvShows(title: String): Flow<List<TvShow>> =
        localDataSource.getSearchTvShows(title).map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntities(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShowEntity, state)
        }
    }

}