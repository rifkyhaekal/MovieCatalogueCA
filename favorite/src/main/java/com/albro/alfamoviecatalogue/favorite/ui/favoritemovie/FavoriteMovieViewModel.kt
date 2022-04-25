package com.albro.alfamoviecatalogue.favorite.ui.favoritemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<Movie>> = movieUseCase.getFavoriteMovies().asLiveData()
}