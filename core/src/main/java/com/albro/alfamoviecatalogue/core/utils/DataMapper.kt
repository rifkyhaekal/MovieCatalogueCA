package com.albro.alfamoviecatalogue.core.utils

import com.albro.alfamoviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.albro.alfamoviecatalogue.core.data.source.local.entity.MovieEntity
import com.albro.alfamoviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.albro.alfamoviecatalogue.core.data.source.local.entity.TvShowEntity
import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvShowResponse
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.model.MovieDetail
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.model.TvShowDetail

object DataMapper {
    fun mapMovieResponseToEntities(input: MovieResponse): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.results.map {
            val movie = MovieEntity(
                movieId = it.id,
                imgPoster = it.posterPath,
                title = it.title,
                favorite = false,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                imgPoster = it.imgPoster,
                title = it.title,
                favorite = it.favorite
            )
        }

    fun mapMovieDetailEntityToDomain(input: MovieEntity): Movie {
        val movie = Movie(
            movieId = input.movieId,
            imgPoster = input.imgPoster,
            title = input.title,
            favorite = input.favorite
        )
        movie.movieDetail = MovieDetail(
            genre = input.movieDetailEntity?.genre,
            overview = input.movieDetailEntity?.overview,
            duration = input.movieDetailEntity?.duration,
            userScore = input.movieDetailEntity?.userScore,
            releaseDate = input.movieDetailEntity?.releaseDate,
            status = input.movieDetailEntity?.status,
            backdropPath = input.movieDetailEntity?.backdropPath
        )

        return movie
    }

    fun mapMovieDomainToEntities(input: Movie): MovieEntity {
        val movieEntity = MovieEntity(
            movieId = input.movieId,
            imgPoster = input.imgPoster,
            title = input.title,
            favorite = input.favorite,
        )

        movieEntity.movieDetailEntity = MovieDetailEntity(
            genre = input.movieDetail?.genre,
            overview = input.movieDetail?.overview,
            duration = input.movieDetail?.duration,
            userScore = input.movieDetail?.userScore ?: 0F,
            releaseDate = input.movieDetail?.releaseDate,
            status = input.movieDetail?.status,
            backdropPath = input.movieDetail?.backdropPath
        )

        return movieEntity
    }

    fun mapTvShowResponseToEntities(input: TvShowResponse): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.results.map {
            val tvShow = TvShowEntity(
                tvShowId = it.id,
                imgPoster = it.posterPath,
                title = it.name,
                favorite = false,
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                tvShowId = it.tvShowId,
                imgPoster = it.imgPoster,
                title = it.title,
                favorite = it.favorite
            )
        }

    fun mapTvShowDetailEntityToDomain(input: TvShowEntity): TvShow {
        val tvShow = TvShow(
            tvShowId = input.tvShowId,
            imgPoster = input.imgPoster,
            title = input.title,
            favorite = input.favorite
        )
        tvShow.tvShowDetail = TvShowDetail(
            genre = input.tvShowDetailEntity?.genre,
            overview = input.tvShowDetailEntity?.overview,
            network = input.tvShowDetailEntity?.network,
            userScore = input.tvShowDetailEntity?.userScore,
            type = input.tvShowDetailEntity?.type,
            status = input.tvShowDetailEntity?.status,
            backdropPath = input.tvShowDetailEntity?.backdropPath
        )

        return tvShow
    }

    fun mapTvShowDomainToEntities(input: TvShow): TvShowEntity {
        val tvShowEntity = TvShowEntity(
            tvShowId = input.tvShowId,
            imgPoster = input.imgPoster,
            title = input.title,
            favorite = input.favorite,
        )

        tvShowEntity.tvShowDetailEntity = TvShowDetailEntity(
            genre = input.tvShowDetail?.genre,
            overview = input.tvShowDetail?.overview,
            network = input.tvShowDetail?.network,
            userScore = input.tvShowDetail?.userScore ?: 0F,
            type = input.tvShowDetail?.type,
            status = input.tvShowDetail?.status,
            backdropPath = input.tvShowDetail?.backdropPath
        )

        return tvShowEntity
    }
}