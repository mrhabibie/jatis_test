package com.habibie.jatistest.data.model.genre

import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(
    @SerializedName("genres")
    val genreList: List<MovieGenre>
)
