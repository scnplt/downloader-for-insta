package com.sertancanpolat.downloaderforinsta.model.helper_class

import com.google.gson.annotations.SerializedName

data class Edges(
    @SerializedName("edges")
    var edges: List<Edge>? = null
)