package com.albro.alfamoviecatalogue.core.domain.model

data class Movie(
    var movieId: Int,
    var imgPoster: String,
    var title: String,
    var favorite: Boolean,
) {
    var movieDetail: MovieDetail? = null
}