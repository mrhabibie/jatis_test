package com.habibie.jatistest.data.repository.movie

import androidx.lifecycle.LiveData
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.movie.MovieDetails
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val apiService: MovieDbInterface) {
    lateinit var movieDetailDataSource: MovieDetailDataSource
    fun fetchMovieDetail(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailDataSource = MovieDetailDataSource(apiService, compositeDisposable)
        movieDetailDataSource.fetchMovieDetail(movieId)

        return movieDetailDataSource.downloadedMovieResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return movieDetailDataSource.networkState
    }
}