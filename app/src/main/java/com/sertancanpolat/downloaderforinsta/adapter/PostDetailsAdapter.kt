package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.data_binding_listeners.PDAItemListener
import com.sertancanpolat.downloaderforinsta.databinding.PdaImageItemBinding
import com.sertancanpolat.downloaderforinsta.databinding.PdaVideoItemBinding
import com.sertancanpolat.downloaderforinsta.model.PostModel
import com.sertancanpolat.downloaderforinsta.model.helper_class.Edge
import com.sertancanpolat.downloaderforinsta.utilities.downloadFile
import com.sertancanpolat.downloaderforinsta.utilities.screenWidth
import com.sertancanpolat.downloaderforinsta.view.PostDetailsActivity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

class PostDetailsAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PDAItemListener {
    lateinit var model: PostModel
    private val typeVideo = 1
    private val typeImg = 0
    lateinit var downloadUrl: String
    var isVideo by Delegates.notNull<Boolean>()

    inner class ImageViewHolder(val view: PdaImageItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(hasChildren: Boolean = false, edge: Edge?) {
            val url: String
            val ratio: Double
            val width: Int
            val height: Int

            if (hasChildren) {
                url = edge?.node?.displayUrl!!
                ratio = view.root.context.screenWidth / edge.node?.dimensions?.width!!.toDouble()
                width = (edge.node?.dimensions?.width!! * ratio).toInt()
                height = (edge.node?.dimensions?.height!! * ratio).toInt()

            } else {
                url = model.graphql?.shortcodeMedia?.displayUrl!!
                ratio = view.root.context.screenWidth / model.graphql?.shortcodeMedia?.dimensions?.width!!.toDouble()
                width = (model.graphql?.shortcodeMedia?.dimensions?.width!! * ratio).toInt()
                height = (model.graphql?.shortcodeMedia?.dimensions?.height!! * ratio).toInt()
            }

            view.pdaItemPostImage.layoutParams.height = height
            view.pdaItemPostImage.layoutParams.width = width
            view.listener = this@PostDetailsAdapter
            view.edge = edge
            view.url = url
        }
    }

    inner class VideoViewHolder(val view: PdaVideoItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(hasChildren: Boolean = false, edge: Edge?) {
            val url = if (hasChildren) edge?.node?.videoUrl!! else model.graphql?.shortcodeMedia?.videoUrl!!

            view.listener = this@PostDetailsAdapter
            view.edge = edge
            view.url = url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == typeImg) {
            val view = DataBindingUtil.inflate<PdaImageItemBinding>(inflater, R.layout.pda_image_item, parent, false)
            ImageViewHolder(view)
        } else {
            val view = DataBindingUtil.inflate<PdaVideoItemBinding>(inflater, R.layout.pda_video_item, parent, false)
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
        val shortcodeMedia = model.graphql?.shortcodeMedia

        if (shortcodeMedia?.edgeSidecarToChildren == null) {
            if (shortcodeMedia?.isVideo!!) (holder as VideoViewHolder).bind(false, null)
             else (holder as ImageViewHolder).bind(false, null)
        } else {
            val edges = shortcodeMedia.edgeSidecarToChildren?.edges

            if (edges?.get(position)?.node?.isVideo!!)
                (holder as VideoViewHolder).bind(true, edges[position])
            else (holder as ImageViewHolder).bind(true, edges[position])
        }
    }

    override fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean) {
        downloadUrl = url
        isVideo = postIsVideo
        downloadPost(v.context)
    }

    override fun shareButtonClicked(v: View, url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(v.context, intent, null)
    }

    private fun downloadPost(context: Context) {
        when (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PackageManager.PERMISSION_GRANTED -> downloadFile(context, downloadUrl, isVideo)
            else -> ActivityCompat.requestPermissions((context as PostDetailsActivity),
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
        }
    }
}