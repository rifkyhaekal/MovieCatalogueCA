package com.example.haekalmoviecatalogue.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.haekalmoviecatalogue.data.source.local.entity.MovieEntity
import com.example.haekalmoviecatalogue.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentities WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("UPDATE movieentities SET genre = :genre, overview = :overview, duration = :duration, userScore = :userScore, releaseDate = :releaseDate, status = :status, backdropPath = :backdropPath WHERE movieId = :movieId")
    suspend fun updateMovieDetail(
        movieId: Int,
        genre: String,
        overview: String,
        duration: String,
        userScore: Float,
        releaseDate: String,
        status: String,
        backdropPath: String
    )

    @Query("SELECT * FROM movieentities WHERE title like :title")
    fun getSearchMovies(title: String): Flow<List<MovieEntity>>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvshowentities WHERE favorite = 1")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvshowentities WHERE tvShowId = :tvShowId")
    fun getTvShowDetail(tvShowId: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Query("UPDATE tvshowentities SET overview = :overview, status = :status, type = :type , genre = :genre, network = :network, userScore = :userScore, backdropPath = :backdropPath WHERE tvShowId = :tvShowId")
    suspend fun updateTvShowDetail(
        tvShowId: Int,
        overview: String,
        status: String,
        type: String,
        genre: String,
        network: String,
        userScore: Float,
        backdropPath: String
    )

    @Query("SELECT * FROM tvshowentities WHERE title like :title")
    fun getSearchTvShows(title: String): Flow<List<TvShowEntity>>
}