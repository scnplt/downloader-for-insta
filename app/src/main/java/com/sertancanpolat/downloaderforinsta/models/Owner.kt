package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("full_name")
    var fullName: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("is_private")
    var isPrivate: Boolean? = null,
    @SerializedName("is_verified")
    var isVerified: Boolean? = null,
    @SerializedName("profile_pic_url")
    var profilePicUrl: String? = null,
    @SerializedName("username")
    var username: String? = null
)