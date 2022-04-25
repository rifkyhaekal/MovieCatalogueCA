package com.albro.alfamoviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class TvShowViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getTvShows(sort: String): LiveData<Resource<List<TvShow>>> =
        movieUseCase.getTvShows(sort).asLiveData()

    fun searchTvShows(title: String): LiveData<List<TvShow>> =
        movieUseCase.getSearchTvShows(title).asLiveData()

}