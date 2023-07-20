package com.habibie.jatistest.data.repository.review

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.review.MovieReview
import io.reactivex.disposables.CompositeDisposable

class ReviewDataSourceFactory(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable,
    private val movieId: Int,
) : DataSource.Factory<Int, MovieReview>() {
    val reviewLiveDataSource = MutableLiveData<ReviewDataSource>()

    override fun create(): DataSource<Int, MovieReview> {
        val reviewDataSource = ReviewDataSource(apiService, compositeDisposable, movieId)

        reviewLiveDataSource.postValue(reviewDataSource)

        return reviewDataSource
    }
}