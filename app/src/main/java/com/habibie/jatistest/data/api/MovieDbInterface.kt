package com.habibie.jatistest.data.api

import com.habibie.jatistest.data.model.genre.MovieGenreResponse
import com.habibie.jatistest.data.model.movie_detail.MovieDetails
import com.habibie.jatistest.data.model.movie.MovieResponse
import com.habibie.jatistest.data.model.review.MovieReviewResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbInterface {
    @GET("/3/genre/movie/list")
    fun getMovieGenre(): Single<MovieGenreResponse>

    @GET("/3/discover/movie")
    fun getDiscoverMovies(
        @Query("with_genres") id: String?,
        @Query("page") page: Int,
    ): Single<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("/3/movie/{movie_id}/reviews")
    fun getMovieReview(@Path("movie_id") id: Int): Single<MovieReviewResponse>
}