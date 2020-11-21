package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.data_binding_interfaces.UAPostItem
import com.sertancanpolat.downloaderforinsta.databinding.UaPostItemBinding
import com.sertancanpolat.downloaderforinsta.model.UserModel
import com.sertancanpolat.downloaderforinsta.model.helper_class.Edge
import com.sertancanpolat.downloaderforinsta.utilities.screenWidth
import com.sertancanpolat.downloaderforinsta.view.PostDetailsActivity
import javax.inject.Inject

class UserPostsAdapter @Inject constructor() : RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>(), UAPostItem {

    lateinit var model: UserModel.Graphql.User

    inner class UserPostViewHolder(var view: UaPostItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(edge: Edge?) {
            val width = view.root.context.screenWidth / 3 - 3

            view.root.layoutParams.height = width
            view.root.layoutParams.width = width
            view.width = width
            view.listener = this@UserPostsAdapter
            view.edge = edge
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<UaPostItemBinding>(inflater, R.layout.ua_post_item, parent, false)
        return UserPostViewHolder(view)
    }

    override fun getItemCount(): Int = model.edgeOwnerToTimelineMedia?.edges?.size ?: 0

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        holder.bind(model.edgeOwnerToTimelineMedia?.edges?.get(position))
    }

    override fun onClicked(v: View, shortCode: String) {
        val intent = Intent(v.context, PostDetailsActivity::class.java)
        intent.putExtra("shortCode", shortCode)
        startActivity(v.context, intent, null)
    }
}