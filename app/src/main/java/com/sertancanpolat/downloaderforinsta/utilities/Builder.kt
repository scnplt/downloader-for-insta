package com.sertancanpolat.downloaderforinsta.utilities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.sertancanpolat.downloaderforinsta.R
import de.mateware.snacky.Snacky

fun snackbarBuilder(
    activity: Activity,
    text: String,
    textSize: Float = 24f,
    icon: Int = R.drawable.ic_info,
    textColor: Int = R.color.colorAccent
) {
    Snacky.builder().setActivity(activity)
        .setBackgroundColor(activity.getColor(R.color.colorPrimaryMid))
        .setText(text)
        .setTextSize(textSize)
        .setTextColor(activity.getColor(textColor))
        .centerText().setDuration(Snacky.LENGTH_SHORT)
        .setIcon(icon).warning().show()
}

fun progressDialogBuilder(context: Context, theme: Int = R.style.CustomDialogTheme, cancelable: Boolean = false): Dialog {
    val dialog = Dialog(context, theme)
    dialog.setContentView(R.layout.loading_dialog)
    dialog.setCancelable(cancelable)
    return dialog
}