package com.habibie.jatistest.data.api

import com.habibie.jatistest.data.model.genre.MovieGenreResponse
import com.habibie.jatistest.data.model.movie.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbInterface {
    @GET("/3/genre/movie/list")
    fun getMovieGenre(): Single<MovieGenreResponse>

    @GET("/3/discover/movie")
    fun getDiscoverMovies(
        @Query("with_genres") id: String?,
        @Query("page") page: Int,
    ): Single<MovieResponse>
}