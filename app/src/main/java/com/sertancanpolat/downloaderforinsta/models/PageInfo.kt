package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("end_cursor")
    var endCursor: String? = null,
    @SerializedName("has_next_page")
    var hasNextPage: Boolean = false
)