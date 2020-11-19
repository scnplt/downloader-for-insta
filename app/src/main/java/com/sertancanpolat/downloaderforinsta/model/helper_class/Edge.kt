package com.sertancanpolat.downloaderforinsta.model.helper_class

import com.google.gson.annotations.SerializedName

data class Edge(
    @SerializedName("node")
    var node: Node? = null
)