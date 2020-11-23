package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class Edges(
    @SerializedName("edges")
    var edges: List<Edge>? = null
)