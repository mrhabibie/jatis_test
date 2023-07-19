package com.habibie.jatistest.data.repository.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.movie_detail.MovieDetails
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailDataSource(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState
    private val _downloadedMovieDetailResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails> get() = _downloadedMovieDetailResponse

    fun fetchMovieDetail(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadedMovieDetailResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("MovieDetailDataSource", it1) }
                    })
            )
        } catch (e: Exception) {
            e.message?.let { Log.e("MovieDetailDataSource", it) }
        }
    }
}