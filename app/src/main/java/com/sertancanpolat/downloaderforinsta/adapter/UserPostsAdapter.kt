package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.model.UserModel
import com.sertancanpolat.downloaderforinsta.model.helper_class.Edge
import com.sertancanpolat.downloaderforinsta.utilities.loadImage
import com.sertancanpolat.downloaderforinsta.view.PostDetailsActivity
import kotlinx.android.synthetic.main.ua_post_item.view.*

class UserPostsAdapter(val model: UserModel.Graphql.User?) :
    RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>() {

    class UserPostViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(edge: Edge?) {
            val width = view.resources.displayMetrics.widthPixels / 3 - 3
            view.layoutParams.width = width
            view.layoutParams.height = width
            view.ua_item_imgViewPost.loadImage(
                url = edge?.node?.displayUrl,
                width = width,
                height = width
            )
            view.ua_item_imgViewPost.setOnClickListener {
                val intent = Intent(view.context, PostDetailsActivity::class.java)
                intent.putExtra("shortCode", edge?.node?.shortcode)
                startActivity(view.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ua_post_item, parent, false)
        return UserPostViewHolder(view)
    }

    override fun getItemCount(): Int = model?.edgeOwnerToTimelineMedia?.edges?.size ?: 0

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        holder.bind(model?.edgeOwnerToTimelineMedia?.edges?.get(position))
    }
}