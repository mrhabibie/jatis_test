package com.habibie.jatistest.data.repository.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.api.POST_PER_PAGE
import com.habibie.jatistest.data.model.review.MovieReview
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ReviewPagedListRepository(private val apiService: MovieDbInterface) {
    lateinit var reviewPagedList: LiveData<PagedList<MovieReview>>
    lateinit var reviewDataSourceFactory: ReviewDataSourceFactory

    fun fetchReview(compositeDisposable: CompositeDisposable, movieId: Int):
            LiveData<PagedList<MovieReview>> {
        reviewDataSourceFactory = ReviewDataSourceFactory(
            apiService, compositeDisposable,
            movieId,
        )

        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE).build()

        reviewPagedList = LivePagedListBuilder(reviewDataSourceFactory, config).build()

        return reviewPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<ReviewDataSource, NetworkState>(
            reviewDataSourceFactory.reviewLiveDataSource, ReviewDataSource::networkState
        )
    }
}