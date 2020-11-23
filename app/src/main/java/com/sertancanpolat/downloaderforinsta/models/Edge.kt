package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class Edge(
    @SerializedName("node")
    var node: Node? = null
)