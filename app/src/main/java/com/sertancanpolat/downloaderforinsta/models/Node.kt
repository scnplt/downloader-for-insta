package com.sertancanpolat.downloaderforinsta.models

import com.google.gson.annotations.SerializedName

data class Node(
    @SerializedName("dimensions")
    var dimensions: Dimensions? = null,
    @SerializedName("display_resources")
    var displayResources: List<DisplayResource>? = null,
    @SerializedName("display_url")
    var displayUrl: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("is_video")
    var isVideo: Boolean = false,
    @SerializedName("media_preview")
    var mediaPreview: String? = null,
    @SerializedName("shortcode")
    var shortcode: String? = null,
    @SerializedName("tracking_token")
    var trackingToken: String? = null,
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("edge_media_preview_like")
    var edgeMediaPreviewLike: EdgeMediaCount? = null,
    @SerializedName("edge_media_to_caption")
    var edgeMediaToCaption: Edges? = null,
    @SerializedName("edge_media_to_comment")
    var edgeMediaToComment: EdgeMediaCount? = null,
    @SerializedName("owner")
    var owner: Owner? = null,
    @SerializedName("taken_at_timestamp")
    var takenAtTimestamp: Int? = null,
    @SerializedName("thumbnail_resources")
    var thumbnailResources: List<DisplayResource>? = null,
    @SerializedName("thumbnail_src")
    var thumbnailSrc: String? = null,
    @SerializedName("video_view_count")
    var videoViewCount: Int? = null,
    @SerializedName("edge_sidecar_to_children")
    var edgeSidecarToChildren: EdgeOwnerToTimelineMedia? = null,
    @SerializedName("video_url")
    var videoUrl: String? = null
)