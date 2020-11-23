package com.sertancanpolat.downloaderforinsta.adapters

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
import com.sertancanpolat.downloaderforinsta.activities.PostDetailsActivity
import com.sertancanpolat.downloaderforinsta.databinding.PdaImageItemBinding
import com.sertancanpolat.downloaderforinsta.databinding.PdaVideoItemBinding
import com.sertancanpolat.downloaderforinsta.interfaces.PDAItemListener
import com.sertancanpolat.downloaderforinsta.models.Edge
import com.sertancanpolat.downloaderforinsta.models.PostModel
import com.sertancanpolat.downloaderforinsta.utils.downloadFile
import com.sertancanpolat.downloaderforinsta.utils.screenWidth
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PostDetailsAdapter @Inject constructor(var postModel: PostModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PDAItemListener {

    private val typeVideo = 1
    private val typeImg = 0
    lateinit var downloadUrl: String
    var isVideo = false

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
                url = postModel.graphql?.shortcodeMedia?.displayUrl!!
                ratio = view.root.context.screenWidth / postModel.graphql?.shortcodeMedia?.dimensions?.width!!.toDouble()
                width = (postModel.graphql?.shortcodeMedia?.dimensions?.width!! * ratio).toInt()
                height = (postModel.graphql?.shortcodeMedia?.dimensions?.height!! * ratio).toInt()
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
            val url = if (hasChildren) edge?.node?.videoUrl!! else postModel.graphql?.shortcodeMedia?.videoUrl!!

            view.listener = this@PostDetailsAdapter
            view.edge = edge
            view.url = url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == typeImg) {
            val view = DataBindingUtil.inflate<PdaImageItemBinding>(inflater, R.layout.pda_image_item,parent, false)
            ImageViewHolder(view)
        } else {
            val view = DataBindingUtil.inflate<PdaVideoItemBinding>(inflater, R.layout.pda_video_item, parent, false)
            VideoViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (postModel.graphql?.shortcodeMedia?.edgeSidecarToChildren == null) 1
        else postModel.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (postModel.graphql?.shortcodeMedia?.edgeSidecarToChildren == null) {
            if (postModel.graphql?.shortcodeMedia?.isVideo!!) typeVideo
            else typeImg
        } else {
            if (postModel.graphql?.shortcodeMedia?.edgeSidecarToChildren?.edges?.get(position)?.node?.isVideo!!) typeVideo
            else typeImg
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shortcodeMedia = postModel.graphql?.shortcodeMedia

        if (shortcodeMedia?.edgeSidecarToChildren == null) {
            if (shortcodeMedia?.isVideo!!) (holder as VideoViewHolder).bind(false, null)
            else (holder as ImageViewHolder).bind(false, null)
        } else {
            val edges = shortcodeMedia.edgeSidecarToChildren?.edges

            if (edges?.get(position)?.node?.isVideo!!) (holder as VideoViewHolder).bind(true, edges[position])
            else (holder as ImageViewHolder).bind(true, edges[position])
        }
    }

    override fun downloadButtonClicked(v: View, url: String, postIsVideo: Boolean) {
        downloadUrl = url
        isVideo = postIsVideo

        if (ContextCompat.checkSelfPermission(v.context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED)  downloadFile(v.context, url, postIsVideo)
        else ActivityCompat.requestPermissions((v.context as PostDetailsActivity),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)

    }

    override fun shareButtonClicked(v: View, url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(v.context, intent, null)
    }
}