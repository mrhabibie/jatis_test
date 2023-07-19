package com.habibie.jatistest.data.model.review

import com.google.gson.annotations.SerializedName

data class MovieReview(
    val author: String,
    @SerializedName("author_details")
    val authorDetails: MovieAuthorDetails,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)