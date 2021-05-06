package com.drinfi.ticklemediaassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.data.GithubServiceData

class GithubReposAdapter(
    private var list: List<GithubServiceData>,
    var selectedRepo: (GithubServiceData) -> Unit
) :
    RecyclerView.Adapter<GithubReposAdapter.RepositoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repository: GithubServiceData = list[position]
        holder.itemView.setOnClickListener { v -> selectedRepo(repository) }
        return holder.bind(repository, position)
    }

    fun updateData(updatedList: List<GithubServiceData>) {
        list = updatedList
        notifyDataSetChanged()
    }


    class RepositoryHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.repository_list_item, parent, false)) {

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
}