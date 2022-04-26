package com.albro.alfamoviecatalogue.di

import android.app.Application
import com.albro.alfamoviecatalogue.core.BuildConfig
import com.albro.alfamoviecatalogue.core.di.databaseModule
import com.albro.alfamoviecatalogue.core.di.networkModule
import com.albro.alfamoviecatalogue.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}