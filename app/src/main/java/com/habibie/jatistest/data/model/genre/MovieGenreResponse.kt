package com.habibie.jatistest.data.model.genre

import com.google.gson.annotations.SerializedName
import com.habibie.jatistest.data.model.movie.MovieGenre

data class MovieGenreResponse(
    @SerializedName("genres")
    val genreList: List<MovieGenre>
)
