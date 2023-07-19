package com.habibie.jatistest.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {
    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}