package com.albro.alfamoviecatalogue.core.domain.model

data class MovieDetail(
    var genre: String?,
    var overview: String?,
    var duration: String?,
    var userScore: Float?,
    var releaseDate: String?,
    var status: String?,
    var backdropPath: String?
)