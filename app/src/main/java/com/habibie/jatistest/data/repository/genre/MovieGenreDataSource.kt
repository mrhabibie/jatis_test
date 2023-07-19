package com.habibie.jatistest.data.repository.genre

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.habibie.jatistest.data.api.FIRST_PAGE
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.model.genre.MovieGenre
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieGenreDataSource(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieGenre>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieGenre>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieGenre()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.genreList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("MovieGenreDataSource", it1) }
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieGenre>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieGenre()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("MovieGenreDataSource", it1) }
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieGenre>) {

    }
}