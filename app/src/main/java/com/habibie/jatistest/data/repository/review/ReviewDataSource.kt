package com.habibie.jatistest.data.repository.review

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.habibie.jatistest.data.api.FIRST_PAGE
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.review.MovieReview
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReviewDataSource(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable,
    private val movieId: Int,
) : PageKeyedDataSource<Int, MovieReview>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieReview>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieReview(id = movieId, page = params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.reviewList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("ReviewDataSource", it1) }
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieReview>) {
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieReview>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieReview(id = movieId, page = page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.reviewList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("ReviewDataSource", it1) }
                    }
                )
        )
    }
}