package com.albro.alfamoviecatalogue.core.data.source.remote.network

import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieDetailResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") api_key: String
    ): MovieDetailResponse

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TvShowResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tv_id: Int?,
        @Query("api_key") api_key: String
    ): TvShowDetailResponse

}