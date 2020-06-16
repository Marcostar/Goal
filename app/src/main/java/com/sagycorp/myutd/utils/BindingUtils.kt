package com.sagycorp.myutd.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sagycorp.myutd.R
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.ui.searchteam.RestApiStatus

@BindingAdapter("imageUrl")
fun setImageUrl(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .dontTransform()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("imageTitle")
fun TextView.setImageTitle(item: String) {
    text = item
}


@BindingAdapter("restApiStatus")
fun bindStatus(statusImageView: ImageView, status: RestApiStatus?) {
    when (status) {
        RestApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RestApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        RestApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}