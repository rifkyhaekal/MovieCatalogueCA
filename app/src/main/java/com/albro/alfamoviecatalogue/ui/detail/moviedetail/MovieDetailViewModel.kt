package com.albro.alfamoviecatalogue.ui.detail.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class MovieDetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getMovieDetail(movieId: Int): LiveData<Resource<Movie>> =
        movieUseCase.getMovieDetail(movieId).asLiveData()

    fun setFavorite(movie: Movie, newState: Boolean) =
        movieUseCase.setMovieFavorite(movie, newState)

}