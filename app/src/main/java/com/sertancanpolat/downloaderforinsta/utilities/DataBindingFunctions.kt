package com.sertancanpolat.downloaderforinsta.utilities

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:downloadUrl", "android:downloadIsCircle")
fun downloadImage(view: ImageView, url: String?, isCircle: Boolean) = view.loadImage(url, isCircle = isCircle)


@BindingAdapter("android:isPrivate")
fun isPrivate(view: ImageView, isPrivate: Boolean){
    if (isPrivate) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("android:isVerified")
fun isVerified(view: ImageView, isVerified: Boolean){
    if (isVerified) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}