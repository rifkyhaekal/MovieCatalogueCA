package com.albro.alfamoviecatalogue.core.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.albro.alfamoviecatalogue.core.data.source.remote.network.ApiService
import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieDetailResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvShowResponse
import com.albro.alfamoviecatalogue.core.utils.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getMovies(): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val response = apiService.getPopularMovie(Common.API_KEY, "en-us", 1)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getMovies: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int?): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                val response = apiService.getMovieDetail(movieId, Common.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getMovieDetail: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<TvShowResponse>> {
        return flow {
            try {
                val response = apiService.getPopularTv(Common.API_KEY, "en-us", 1)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getTvShows: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(tvShowId: Int?): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                val response = apiService.getTvDetail(tvShowId, Common.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getMovieDetail: $e")
            }
        }.flowOn(Dispatchers.IO)
    }
}