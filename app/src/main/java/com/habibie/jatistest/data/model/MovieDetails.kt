package com.habibie.jatistest.data.model

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: MovieBelongsToCollection,
    val budget: Long,
    val movieGenres: List<MovieGenre>,
    val homepage: String,
    val id: Long,
    val imdbID: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<MovieProductionCompany>,
    val productionCountries: List<MovieProductionCountry>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<MovieSpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
)
