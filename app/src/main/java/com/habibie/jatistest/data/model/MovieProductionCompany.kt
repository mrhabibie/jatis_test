package com.habibie.jatistest.data.model

data class MovieProductionCompany(
    val id: Long,
    val logoPath: String? = null,
    val name: String,
    val originCountry: String
)
