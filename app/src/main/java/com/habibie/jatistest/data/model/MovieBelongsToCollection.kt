package com.habibie.jatistest.data.model

data class MovieBelongsToCollection(
    val id: Long,
    val name: String,
    val posterPath: String,
    val backdropPath: Any? = null
)
