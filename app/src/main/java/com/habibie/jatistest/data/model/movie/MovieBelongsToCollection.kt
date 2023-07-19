package com.habibie.jatistest.data.model.movie

data class MovieBelongsToCollection(
    val id: Long,
    val name: String,
    val posterPath: String,
    val backdropPath: Any? = null
)
