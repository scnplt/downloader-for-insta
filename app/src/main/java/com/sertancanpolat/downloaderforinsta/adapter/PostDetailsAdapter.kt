package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.model.PostModel
import com.sertancanpolat.downloaderforinsta.model.helper_class.Edge
import com.sertancanpolat.downloaderforinsta.utilities.downloadFile
import com.sertancanpolat.downloaderforinsta.view.PostDetailsActivity
import kotlinx.android.synthetic.main.pda_image_item.view.*
import kotlinx.android.synthetic.main.pda_video_item.view.*
import kotlin.properties.Delegates

class PostDetailsAdapter(val model: PostModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val typeVideo = 1
    private val typeImg = 0
    lateinit var downloadUrl: String
    var postIsVideo by Delegates.notNull<Boolean>()

    inner class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hasChildren: Boolean = false, edge: Edge?) {
            val url: String
            val ratio: Double
            val width: Int
            val height: Int

            if (hasChildren) {
                url = edge?.node?.displayUrl!!
                ratio = view.resources.displayMetrics.widthPixels / edge.node?.dimensions?.width!!.toDouble()
                width = (edge.node?.dimensions?.width!! * ratio).toInt()
                height = (edge.node?.dimensions?.height!! * ratio).toInt()

            } else {
                url = model.graphql?.shortcodeMedia?.displayUrl!!
                ratio = view.resources.displayMetrics.widthPixels / model.graphql?.shortcodeMedia?.dimensions?.width!!.toDouble()
                width = (model.graphql?.shortcodeMedia?.dimensions?.width!! * ratio).toInt()
                height = (model.graphql?.shortcodeMedia?.dimensions?.height!! * ratio).toInt()
            }


            view.pda_item_postImage.layoutParams.width = width
            view.pda_item_postImage.layoutParams.height = height


            view.pda_item_downloadPostImage.setOnClickListener {
                downloadUrl = url
                postIsVideo = false
                downloadPost(view.pda_item_postImage.context)
            }


        }
    }

    inner class VideoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hasChildren: Boolean = false, edge: Edge?) {
            val context = view.pda_item_postVideo.context

            val exoPlayer = SimpleExoPlayer.Builder(context).build()
            val defaultDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, R.string.app_name.toString()))
            val mediaSource = ProgressiveMediaSource.Factory(defaultDataSourceFactory)

            val url = if (hasChildren) edge?.node?.videoUrl!!
            else model.graphql?.shortcodeMedia?.videoUrl!!

            view.pda_item_postVideo.player = exoPlayer
            view.pda_item_postVideo.requestFocus()

            view.pda_item_postVideo.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(p0: View?) {
                    exoPlayer.stop()
                }

                override fun onViewAttachedToWindow(p0: View?) {
                    exoPlayer.prepare(mediaSource.createMediaSource(Uri.parse(url)))
                    exoPlayer.playWhenReady = false
                }
            })

            view.pda_item_downloadPostVideo.setOnClickListener {
                downloadUrl = url
                postIsVideo = true
                downloadPost(view.pda_item_postVideo.context)
            }

            val shareButton = view.pda_item_share_buttonVideo
            shareButton.setOnClickListener { sharePost(view, url) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == typeImg) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.pda_image_item, parent, false)
            ImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.pda_video_item, parent, false)
            VideoViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (model.graphql?.shortcodeMedia?.edgeSidecarToChildren == null) 1
        else model.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (model.graphql?.shortcodeMedia?.edgeSidecarToChildren == null) {
            if (model.graphql?.shortcodeMedia?.isVideo!!) typeVideo
            else typeImg
        } else {
            if (model.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.get(position)?.node?.isVideo!!) typeVideo
            else typeImg
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (model.graphql?.shortcodeMedia?.edgeSidecarToChildren == null) {
            if (model.graphql?.shortcodeMedia?.isVideo!!) {
                (holder as VideoViewHolder).bind(false, null)
            } else {
                (holder as ImageViewHolder).bind(false, null)
            }
        } else {
            if (model.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.get(position)?.node?.isVideo!!)
                (holder as VideoViewHolder).bind(
                    true,
                    model.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.get(position)!!
                )
            else {
                (holder as ImageViewHolder).bind(
                    true,
                    model.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.get(position)!!
                )
            }
        }
    }

    private fun downloadPost(context: Context) {
        when (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PackageManager.PERMISSION_GRANTED -> downloadFile(context, downloadUrl, postIsVideo)
            else -> ActivityCompat.requestPermissions((context as PostDetailsActivity), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

    }

    private fun sharePost(view: View, url: String){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        view.context.startActivity(intent)
    }
}