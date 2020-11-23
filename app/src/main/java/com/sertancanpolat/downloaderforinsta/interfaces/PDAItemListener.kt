package com.sertancanpolat.downloaderforinsta.interfaces

import android.view.View

interface PDAItemListener {
    fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean)
    fun shareButtonClicked(v: View, url: String)
}