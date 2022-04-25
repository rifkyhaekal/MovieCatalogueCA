package com.albro.alfamoviecatalogue.core.domain.model


data class TvShow(
    var tvShowId: Int,
    var imgPoster: String,
    var favorite: Boolean,
    var title: String,
) {
    var tvShowDetail: TvShowDetail? = null
}