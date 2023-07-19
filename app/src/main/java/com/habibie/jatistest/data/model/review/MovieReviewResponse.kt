package com.habibie.jatistest.data.model.review

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val reviewList: List<MovieReview>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)
