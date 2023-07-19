package com.habibie.jatistest.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class MovieProductionCountry(
    @SerializedName("iso_3166_1")
    val iso3166_1: String,
    val name: String
)
