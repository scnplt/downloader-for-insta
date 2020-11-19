package com.sertancanpolat.downloaderforinsta.utilities

import android.widget.ImageView
import com.sertancanpolat.downloaderforinsta.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

fun ImageView.loadImage(url: String?, width: Int = 0, height: Int = 0, isCircle: Boolean = false) {

    val picasso = Picasso.get()
        .load(url)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.ic_refresh)

    if (isCircle) picasso.transform(CropCircleTransformation())
    if (width != 0 && height != 0) picasso.resize(width, height).centerCrop()

    picasso.into(this)
}