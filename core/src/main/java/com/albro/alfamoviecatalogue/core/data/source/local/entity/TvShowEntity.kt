package com.example.haekalmoviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    var tvShowId: Int,

    @ColumnInfo(name = "imgPoster")
    var imgPoster: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false

) {
    @Embedded
    var tvShowDetailEntity: TvShowDetailEntity? = null
}
