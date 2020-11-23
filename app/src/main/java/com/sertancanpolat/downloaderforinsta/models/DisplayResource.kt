package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class DisplayResource(
    @SerializedName("config_height")
    var configHeight: Int? = null,
    @SerializedName("config_width")
    var configWidth: Int? = null,
    @SerializedName("src")
    var src: String? = null
)