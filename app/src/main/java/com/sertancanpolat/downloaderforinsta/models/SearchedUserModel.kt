package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class SearchedUserModel(
    @SerializedName("users")
    var users: List<UserWithPosition>? = null
) {
    data class UserWithPosition(
        @SerializedName("position")
        var position: Int? = null,
        @SerializedName("user")
        var user: User? = null
    ) {
        data class User(
            @SerializedName("full_name")
            var fullName: String? = null,
            @SerializedName("is_private")
            var isPrivate: Boolean = false,
            @SerializedName("is_verified")
            var isVerified: Boolean = false,
            @SerializedName("pk")
            var pk: String = "",
            @SerializedName("profile_pic_id")
            var profilePicId: String? = null,
            @SerializedName("profile_pic_url")
            var profilePicUrl: String = "",
            @SerializedName("username")
            var username: String? = null
        )
    }
}
