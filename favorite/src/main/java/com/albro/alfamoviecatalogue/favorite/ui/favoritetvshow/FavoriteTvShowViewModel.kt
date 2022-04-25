package com.albro.alfamoviecatalogue.favorite.ui.favoritetvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class FavoriteTvShowViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getFavorieTvShows(): LiveData<List<TvShow>> = movieUseCase.getFavoriteTvShow().asLiveData()
}