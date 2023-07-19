package com.habibie.jatistest.data.repository.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.api.POST_PER_PAGE
import com.habibie.jatistest.data.model.genre.MovieGenre
import com.habibie.jatistest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieGenrePagedListRepository(private val apiService: MovieDbInterface) {
    lateinit var moviePagedList: LiveData<PagedList<MovieGenre>>
    lateinit var moviesGenreDataSourceFactory: MovieGenreDataSourceFactory

    fun fetchMovieGenrePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<MovieGenre>> {
        moviesGenreDataSourceFactory = MovieGenreDataSourceFactory(apiService, compositeDisposable)

        val config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(POST_PER_PAGE)
                .build()

        moviePagedList = LivePagedListBuilder(moviesGenreDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieGenreDataSource, NetworkState>(
            moviesGenreDataSourceFactory.movieGenreLiveDataSource,
            MovieGenreDataSource::networkState
        )
    }
}