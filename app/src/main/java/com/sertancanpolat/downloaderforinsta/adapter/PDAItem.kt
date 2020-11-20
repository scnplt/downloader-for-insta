package com.sertancanpolat.downloaderforinsta.adapter

import android.view.View

interface PDAItem {
    fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean)
    fun shareButtonClicked(v: View, url: String)
}