package com.habibie.jatistest.data.repository.genre

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.genre.MovieGenre
import io.reactivex.disposables.CompositeDisposable

class MovieGenreDataSourceFactory(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieGenre>() {
    val movieGenreLiveDataSource = MutableLiveData<MovieGenreDataSource>()

    override fun create(): DataSource<Int, MovieGenre> {
        val movieDataSource = MovieGenreDataSource(apiService, compositeDisposable)

        movieGenreLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}