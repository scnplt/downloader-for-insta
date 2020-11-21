package com.sertancanpolat.downloaderforinsta.utilities

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sertancanpolat.downloaderforinsta.R

@BindingAdapter("android:downloadUrl", "android:downloadIsCircle")
fun downloadImage(view: ImageView, url: String?, isCircle: Boolean) = view.loadImage(url, isCircle = isCircle)

@BindingAdapter("android:downloadUrl", "android:downloadIsCircle", "android:downloadWidth")
fun downloadImageWithWidth(view: ImageView, url: String?, isCircle: Boolean, width: Int){
    view.loadImage(url, width, width, isCircle)
}

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

@BindingAdapter("android:loadVideoURL")
fun loadVideo(view: PlayerView, url: String){
    val context = view.context
    val exoPlayer = SimpleExoPlayer.Builder(context).build()
    val defaultDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, R.string.app_name.toString()))
    val mediaSource = ProgressiveMediaSource.Factory(defaultDataSourceFactory)

    view.player = exoPlayer
    view.requestFocus()

    exoPlayer.prepare(mediaSource.createMediaSource(Uri.parse(url)))
    exoPlayer.playWhenReady = false

    view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
        override fun onViewAttachedToWindow(p0: View?) {}
        override fun onViewDetachedFromWindow(p0: View?) {
            exoPlayer.playWhenReady = false
        }
    })
}