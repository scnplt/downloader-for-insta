package com.sertancanpolat.downloaderforinsta.data_binding_listeners

import android.view.View

interface PDAItemListener {
    fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean)
    fun shareButtonClicked(v: View, url: String)
}