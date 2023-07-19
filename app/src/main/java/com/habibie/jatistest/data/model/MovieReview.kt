package com.habibie.jatistest.data.model

data class MovieReview(
    val author: String,
    val authorDetails: MovieAuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)