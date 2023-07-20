package com.habibie.jatistest.data.model.review

import com.google.gson.annotations.SerializedName

data class MovieAuthorDetails(
    val name: String,
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    val rating: Double? = null
)