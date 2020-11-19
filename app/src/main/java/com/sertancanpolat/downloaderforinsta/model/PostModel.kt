package com.sertancanpolat.downloaderforinsta.model

import com.google.gson.annotations.SerializedName
import com.sertancanpolat.downloaderforinsta.model.helper_class.*

data class PostModel(
    @SerializedName("graphql")
    var graphql: Graphql? = null
) {
    data class Graphql(
        @SerializedName("shortcode_media")
        var shortcodeMedia: ShortcodeMedia? = null
    )

    data class ShortcodeMedia(
        @SerializedName("dimensions")
        var dimensions: Dimensions? = null,
        @SerializedName("display_resources")
        var displayResources: List<DisplayResource>? = null,
        @SerializedName("display_url")
        var displayUrl: String? = null,
        @SerializedName("edge_media_preview_comment")
        var edgeMediaPreviewComment: EdgeMediaCount? = null,
        @SerializedName("edge_media_preview_like")
        var edgeMediaPreviewLike: EdgeMediaCount? = null,
        @SerializedName("edge_media_to_caption")
        var edgeMediaToCaption: Edges? = null,
        @SerializedName("edge_media_to_parent_comment")
        var edgeMediaToParentComment: EdgeMediaCount? = null,
        @SerializedName("edge_sidecar_to_children")
        var edgeSidecarToChildren: Edges? = null,
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("is_video")
        var isVideo: Boolean? = null,
        @SerializedName("owner")
        var owner: Owner? = null,
        @SerializedName("shortcode")
        var shortcode: String? = null,
        @SerializedName("taken_at_timestamp")
        var takenAtTimestamp: Int? = null,
        @SerializedName("tracking_token")
        var trackingToken: String? = null,
        @SerializedName("video_url")
        var videoUrl: String? = null
    )
}

