package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class EdgeMediaCount(
    @SerializedName("count")
    var count: Int? = null
)