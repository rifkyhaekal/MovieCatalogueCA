package com.example.haekalmoviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("genres")
    val genres: List<TvGenresItem>,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("networks")
    val networks: List<NetworksItem>,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String

)

data class TvGenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

data class NetworksItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,
)
