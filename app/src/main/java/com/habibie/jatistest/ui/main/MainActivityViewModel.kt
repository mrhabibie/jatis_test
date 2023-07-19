package com.habibie.jatistest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.habibie.jatistest.data.model.movie.Movie
import com.habibie.jatistest.data.model.movie.MovieGenre
import com.habibie.jatistest.data.repository.NetworkState
import com.habibie.jatistest.data.repository.genre.MovieGenrePagedListRepository
import com.habibie.jatistest.data.repository.movie.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val movieRepository: MoviePagedListRepository,
    private val movieGenreRepository: MovieGenrePagedListRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchMoviePagedList(compositeDisposable)
    }

    val movieGenrePagedList: LiveData<PagedList<MovieGenre>> by lazy {
        movieGenreRepository.fetchMovieGenrePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    val networkStateGenre: LiveData<NetworkState> by lazy {
        movieGenreRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    fun listGenreIsEmpty(): Boolean {
        return movieGenrePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}