package com.albro.alfamoviecatalogue.core.di

import androidx.room.Room
import com.albro.alfamoviecatalogue.core.BuildConfig
import com.albro.alfamoviecatalogue.core.data.source.MovieRepository
import com.albro.alfamoviecatalogue.core.data.source.local.LocalDataSource
import com.albro.alfamoviecatalogue.core.data.source.remote.network.ApiService
import com.albro.alfamoviecatalogue.core.domain.repository.IMovieRepository
import com.example.haekalmoviecatalogue.data.source.local.room.MovieDatabase
import com.example.haekalmoviecatalogue.data.source.remote.RemoteDataSource
import com.example.haekalmoviecatalogue.utils.AppExecutors
import com.example.haekalmoviecatalogue.utils.Common
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movieentities"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Common.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}