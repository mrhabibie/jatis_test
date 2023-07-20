package com.habibie.jatistest.ui.main.adapter.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibie.jatistest.R
import com.habibie.jatistest.data.api.POSTER_BASE_URL
import com.habibie.jatistest.data.model.review.MovieReview
import com.habibie.jatistest.data.repository.NetworkState
import kotlinx.android.synthetic.main.movie_review_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class ReviewPagedListAdapter(public val context: Context) :
    PagedListAdapter<MovieReview, RecyclerView.ViewHolder>(ReviewDiffCallback()) {
    val REVIEW_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == REVIEW_VIEW_TYPE) {
            (holder as ReviewItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        return if (viewType == REVIEW_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_review_list_item, parent, false)
            ReviewItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            NetworkStateItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            REVIEW_VIEW_TYPE
        }
    }

    class ReviewDiffCallback : DiffUtil.ItemCallback<MovieReview>() {
        override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
            return oldItem == newItem
        }
    }

    class ReviewItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(review: MovieReview?, context: Context) {
            itemView.review_author.text = review?.author
            itemView.review_rating.text = review?.authorDetails?.rating?.toString()
            itemView.review_date.text = review?.createdAt
            itemView.review_content.text = review?.content

            val reviewAvatarURL: String = if (review?.authorDetails?.avatarPath == null) {
                "https://www.gravatar.com/avatar/00000000000000000000000000000000"
            } else if (review.authorDetails.avatarPath.contains("https")) {
                review.authorDetails.avatarPath.drop(1)
            } else {
                POSTER_BASE_URL + review.authorDetails.avatarPath;
            }

            Glide.with(itemView.context).load(reviewAvatarURL)
                .into(itemView.review_avatar)
        }
    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            } else {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            } else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }
}