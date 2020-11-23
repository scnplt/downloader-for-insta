package com.sertancanpolat.downloaderforinsta.utils

fun getShortCodeFromUrl(text: String): String {
    val index = when {
        text.contains("https://www.instagram.com/p/") -> 28
        text.contains("https://www.instagram.com/tv/") -> 29
        else -> null
    }

    var shortCode = " "
    index?.let {
        shortCode = if (text.length != index) {
            val url = if (text.last() != '/') "$text/" else text
            url.subSequence(index, url.indexOf("/", index)).toString()
        } else " "
    }
    return shortCode
}