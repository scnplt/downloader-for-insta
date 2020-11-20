package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.databinding.UaPostItemBinding
import com.sertancanpolat.downloaderforinsta.model.UserModel
import com.sertancanpolat.downloaderforinsta.model.helper_class.Edge
import com.sertancanpolat.downloaderforinsta.utilities.loadImage
import com.sertancanpolat.downloaderforinsta.view.PostDetailsActivity

class UserPostsAdapter(val model: UserModel.Graphql.User?) :
    RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>(),
    UAPostItem {

    inner class UserPostViewHolder(var view: UaPostItemBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(edge: Edge?) {
            view.edge = edge
            view.listener = this@UserPostsAdapter

            val width = view.root.resources.displayMetrics.widthPixels / 3 - 3
            view.root.layoutParams.width = width
            view.root.layoutParams.height = width
            view.uaItemImgViewPost.loadImage(
                url = edge?.node?.displayUrl,
                width = width,
                height = width
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<UaPostItemBinding>(
            inflater,
            R.layout.ua_post_item,
            parent,
            false
        )
        return UserPostViewHolder(view)
    }

    override fun getItemCount(): Int = model?.edgeOwnerToTimelineMedia?.edges?.size ?: 0

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) =
        holder.bind(model?.edgeOwnerToTimelineMedia?.edges?.get(position))

    override fun onClicked(v: View, shortCode: String) {
        val intent = Intent(v.context, PostDetailsActivity::class.java)
        intent.putExtra("shortCode", shortCode)
        startActivity(v.context, intent, null)
    }
}