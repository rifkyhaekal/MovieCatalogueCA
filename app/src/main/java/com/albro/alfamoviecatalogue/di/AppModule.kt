package com.albro.alfamoviecatalogue.di

import com.albro.alfamoviecatalogue.core.domain.usecase.MovieInteractor
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase
import com.albro.alfamoviecatalogue.ui.detail.moviedetail.MovieDetailViewModel
import com.albro.alfamoviecatalogue.ui.detail.tvshowdetail.TvShowDetailViewModel
import com.albro.alfamoviecatalogue.ui.movie.MovieViewModel
import com.albro.alfamoviecatalogue.ui.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { TvShowDetailViewModel(get()) }
}