package com.sertancanpolat.downloaderforinsta.adapter

import android.view.View

interface PDAItemDownloadButton {
    fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean)
}