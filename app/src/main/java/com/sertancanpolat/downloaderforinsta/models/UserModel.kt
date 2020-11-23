package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("graphql")
    var graphql: Graphql? = null
) {
    data class Graphql(
        @SerializedName("user")
        var user: User? = null
    ) {
        data class User(
            @SerializedName("biography")
            var biography: String? = null,
            @SerializedName("edge_follow")
            var edgeFollow: EdgeMediaCount? = null,
            @SerializedName("edge_followed_by")
            var edgeFollowedBy: EdgeMediaCount? = null,
            @SerializedName("edge_owner_to_timeline_media")
            var edgeOwnerToTimelineMedia: EdgeOwnerToTimelineMedia? = null,
            @SerializedName("full_name")
            var fullName: String? = null,
            @SerializedName("highlight_reel_count")
            var highlightReelCount: Int? = null,
            @SerializedName("id")
            var id: String? = null,
            @SerializedName("is_private")
            var isPrivate: Boolean = false,
            @SerializedName("is_verified")
            var isVerified: Boolean? = null,
            @SerializedName("profile_pic_url_hd")
            var profilePicUrlHd: String? = null,
            @SerializedName("username")
            var username: String? = null
        )
    }
}