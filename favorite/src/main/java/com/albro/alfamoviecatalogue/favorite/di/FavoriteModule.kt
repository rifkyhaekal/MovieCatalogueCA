package com.albro.alfamoviecatalogue.favorite.di

import com.albro.alfamoviecatalogue.favorite.ui.favoritemovie.FavoriteMovieViewModel
import com.albro.alfamoviecatalogue.favorite.ui.favoritetvshow.FavoriteTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
}