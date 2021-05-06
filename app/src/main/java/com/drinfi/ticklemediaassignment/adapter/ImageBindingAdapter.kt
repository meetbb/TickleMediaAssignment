package com.drinfi.ticklemediaassignment.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.drinfi.ticklemediaassignment.R

object ImageBindingAdapter {
    @BindingAdapter("imageURL")
    @JvmStatic
    fun bindImage(view: ImageView, url: String?) {
        if (url != null) Glide.with(view.context).load(url)
            .apply(
                RequestOptions().placeholder(R.drawable.placeholder).diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
            )
            .into(view)
    }

    @BindingAdapter("customText")
    @JvmStatic
    fun bindText(view: TextView, text: Int?) {
        if (text != null) view.text = text.toString()
    }
}