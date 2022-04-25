package com.albro.alfamoviecatalogue.ui.detail.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.domain.usecase.MovieUseCase

class TvShowDetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShow>> =
        movieUseCase.getTvShowDetail(tvShowId).asLiveData()

    fun setFavorite(tvShow: TvShow, newState: Boolean) =
        movieUseCase.setTvShowFavorite(tvShow, newState)

}