package com.sertancanpolat.downloaderforinsta.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import com.sertancanpolat.downloaderforinsta.R
import com.squareup.picasso.Picasso
import de.mateware.snacky.Snacky
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

fun Activity.snackBar(text: String, textSize: Float = 20f, icon: Int = R.drawable.ic_info) {
    Snacky.builder().setActivity(this)
        .setText(text)
        .setTextSize(textSize)
        .setTextColor(this.getColor(R.color.colorAccent))
        .setIcon(icon).build().show()
}

fun Context.progressDialog(theme: Int = R.style.CustomDialogTheme, cancelable: Boolean = false): Dialog {
    return Dialog(this, theme).apply {
            setContentView(R.layout.loading_dialog)
            setCancelable(cancelable)
    }
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels