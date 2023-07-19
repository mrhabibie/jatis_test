package com.habibie.jatistest.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class MovieSpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso639_1: String,
    val name: String
)
