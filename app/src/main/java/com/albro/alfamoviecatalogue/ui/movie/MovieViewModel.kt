package com.albro.alfamoviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<List<Movie>>> =
        movieUseCase.getMovies(sort).asLiveData()

    fun searchMovies(title: String): LiveData<List<Movie>> =
        movieUseCase.getSearchMovies(title).asLiveData()

}