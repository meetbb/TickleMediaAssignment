package com.drinfi.ticklemediaassignment.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.utils.ILoadMore
import kotlinx.android.synthetic.main.loading_layout.view.*


internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var progressBar = itemView.loading_progress_bar
}

internal class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var repoTitle: TextView? = null
    private var repoIcon: ImageView? = null
    var repoDescription: TextView? = null

    init {
        repoTitle = itemView.findViewById(R.id.repo_title)
        repoIcon = itemView.findViewById(R.id.repository_icon)
        repoDescription = itemView.findViewById(R.id.repo_description)
    }

    fun bind(repository: GithubServiceData, position: Int) {
        repoTitle?.text = repository.login
        repoDescription?.text = repository.type
        Glide.with(itemView.context).load(repository.avatar_url)
            .apply(
                RequestOptions().placeholder(R.drawable.placeholder).diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
            )
            .into(repoIcon)
    }
}

class GithubReposAdapter(
    var activity: Activity,
    var repoRecyclerView: RecyclerView,
    private var list: MutableList<GithubServiceData?>,
    var selectedRepo: (GithubServiceData) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_ITEMTYPE = 0
    val VIEW_LOADINGTYPE = 1

    internal var loadMore: ILoadMore? = null
    internal var isLoading: Boolean = false
    internal var visibleThreshold = 5
    internal var lastVisibleItem = 0
    internal var totalItemCount = 0

    init {
        val linearLayoutManager = repoRecyclerView.layoutManager as LinearLayoutManager
        repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (loadMore != null)
                        loadMore!!.onLoadMore(lastVisibleItem + visibleThreshold)
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_LOADINGTYPE) {
            val view = LayoutInflater.from(activity).inflate(R.layout.loading_layout, parent, false)
            return LoadingViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.repository_list_item, parent, false)
            return ItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_LOADINGTYPE else VIEW_ITEMTYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val repository: GithubServiceData? = list[position]
            holder.itemView.setOnClickListener { v -> selectedRepo(repository!!) }
            return holder.bind(repository!!, position)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setLoadMore(iLoadMore: ILoadMore) {
        this.loadMore = iLoadMore
    }

    fun updateData(updatedList: MutableList<GithubServiceData?>) {
        list = updatedList
        notifyDataSetChanged()
        setLoaded()
    }
}