package com.habibie.jatistest.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.habibie.jatistest.data.model.movie.MovieDetails
import com.habibie.jatistest.data.repository.NetworkState
import com.habibie.jatistest.data.repository.movie.MovieDetailRepository
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(private val movieDetailRepository: MovieDetailRepository, movieId: Int) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieDetail: LiveData<MovieDetails> by lazy {
        movieDetailRepository.fetchMovieDetail(compositeDisposable, movieId)
    }
    val networkState: LiveData<NetworkState> by lazy {
        movieDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}