package com.habibie.jatistest.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.habibie.jatistest.R
import com.habibie.jatistest.data.api.MovieDbClient
import com.habibie.jatistest.data.api.MovieDbInterface
import com.habibie.jatistest.data.api.POSTER_BASE_URL
import com.habibie.jatistest.data.model.movie_detail.MovieDetails
import com.habibie.jatistest.data.repository.NetworkState
import com.habibie.jatistest.data.repository.movie.MovieDetailRepository
import com.habibie.jatistest.data.repository.review.ReviewPagedListRepository
import com.habibie.jatistest.ui.main.adapter.review.ReviewPagedListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var reviewRepository: ReviewPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieId: Int = intent.getIntExtra("id", 1)
        val apiService: MovieDbInterface = MovieDbClient.getClient()
        movieDetailRepository = MovieDetailRepository(apiService)
        reviewRepository = ReviewPagedListRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetail.observe(this) {
            bindUi(it)
        }

        val reviewAdapter = ReviewPagedListAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        review_layout.layoutManager = linearLayoutManager
        review_layout.adapter = reviewAdapter

        viewModel.reviewPagedList.observe(this) {
            reviewAdapter.submitList(it)
        }

        viewModel.networkState.observe(this) {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }
    }

    private fun bindUi(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.voteAverage.toString()
        (it.runtime.toString() + " minutes").also { movie_runtime.text = it }
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);
    }

    private fun getViewModel(movieId: Int): DetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(movieDetailRepository, reviewRepository, movieId) as T
            }
        })[DetailViewModel::class.java]
    }
}