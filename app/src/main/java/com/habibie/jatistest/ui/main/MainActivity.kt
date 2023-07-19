package com.habibie.jatistest.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibie.jatistest.R
import com.habibie.jatistest.data.api.MovieDbClient
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.repository.NetworkState
import com.habibie.jatistest.data.repository.genre.MovieGenrePagedListRepository
import com.habibie.jatistest.data.repository.movie.MoviePagedListRepository
import com.habibie.jatistest.ui.main.adapter.genre.MovieGenrePagedListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository
    lateinit var movieGenreRepository: MovieGenrePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovieDbInterface = MovieDbClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)
        movieGenreRepository = MovieGenrePagedListRepository(apiService)

        viewModel = getViewModel()

        /**
         * START: Set Genre
         */
        val genreAdapter = MovieGenrePagedListAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_genre_list.layoutManager = linearLayoutManager
        rv_genre_list.adapter = genreAdapter

        viewModel.movieGenrePagedList.observe(this, Observer {
            genreAdapter.submitList(it)
        })

        viewModel.networkStateGenre.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listGenreIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listGenreIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listGenreIsEmpty()) {
                genreAdapter.setNetworkState(it)
            }
        })
        /**
         * END: Set Genre
         */

        /**
         * START: Set Movies
         */
        val movieAdapter = MoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1 else 3
            }

        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
        /**
         * END: Set Movies
         */
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository, movieGenreRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}