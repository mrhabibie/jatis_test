package com.habibie.jatistest.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieBelongsToCollection(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: Any? = null
)
