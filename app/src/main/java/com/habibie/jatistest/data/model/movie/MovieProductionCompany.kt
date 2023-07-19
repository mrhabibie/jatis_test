package com.habibie.jatistest.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieProductionCompany(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)
