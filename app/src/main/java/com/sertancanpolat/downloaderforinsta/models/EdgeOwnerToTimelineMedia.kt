package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class EdgeOwnerToTimelineMedia(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("edges")
    var edges: ArrayList<Edge>? = null,
    @SerializedName("page_info")
    var pageInfo: PageInfo? = null
)