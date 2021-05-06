package com.drinfi.ticklemediaassignment.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.drinfi.ticklemediaassignment.R

object ImageBindingAdapter {
    @BindingAdapter("imageURL")
    @JvmStatic
    fun bindImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url)
            .apply(
                RequestOptions().placeholder(R.drawable.placeholder).diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
            )
            .into(view)
    }
}