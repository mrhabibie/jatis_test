package com.habibie.jatistest.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.habibie.jatistest.data.model.movie_detail.MovieDetails
import com.habibie.jatistest.data.model.review.MovieReview
import com.habibie.jatistest.data.repository.NetworkState
import com.habibie.jatistest.data.repository.movie.MovieDetailRepository
import com.habibie.jatistest.data.repository.review.ReviewPagedListRepository
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(
    private val movieDetailRepository: MovieDetailRepository,
    private val reviewRepository: ReviewPagedListRepository,
    movieId: Int,
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieDetail: LiveData<MovieDetails> by lazy {
        movieDetailRepository.fetchMovieDetail(compositeDisposable, movieId)
    }
    val reviewPagedList: LiveData<PagedList<MovieReview>> by lazy {
        reviewRepository.fetchReview(compositeDisposable, movieId)
    }
    val networkState: LiveData<NetworkState> by lazy {
        movieDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}