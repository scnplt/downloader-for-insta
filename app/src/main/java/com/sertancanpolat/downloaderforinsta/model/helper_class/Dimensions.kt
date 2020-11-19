package com.sertancanpolat.downloaderforinsta.model.helper_class

import com.google.gson.annotations.SerializedName

data class Dimensions(
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("width")
    var width: Int? = null
)