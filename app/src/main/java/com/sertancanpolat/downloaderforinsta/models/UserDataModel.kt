package com.sertancanpolat.downloaderforinsta.models


import com.google.gson.annotations.SerializedName


data class UserDataModel(
    @SerializedName("data")
    var data: Data? = null
) {
    data class Data(
        @SerializedName("user")
        var user: User? = null
    ) {
        data class User(
            @SerializedName("edge_owner_to_timeline_media")
            var edgeOwnerToTimelineMedia: EdgeOwnerToTimelineMedia? = null
        )
    }
}

