package com.sertancanpolat.downloaderforinsta.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.activities.PostDetailsActivity
import com.sertancanpolat.downloaderforinsta.databinding.UaPostItemBinding
import com.sertancanpolat.downloaderforinsta.interfaces.UAPostItemListener
import com.sertancanpolat.downloaderforinsta.models.UserModel
import com.sertancanpolat.downloaderforinsta.utils.screenWidth
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserPostsAdapter @Inject constructor(var userModel: UserModel.Graphql.User) :
    RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>(), UAPostItemListener {

    class UserPostViewHolder(var view: UaPostItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<UaPostItemBinding>(inflater, R.layout.ua_post_item, parent, false)
        return UserPostViewHolder(view)
    }

    override fun getItemCount(): Int = userModel.edgeOwnerToTimelineMedia?.edges?.size ?: 0

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        val edge = userModel.edgeOwnerToTimelineMedia?.edges?.get(position)
        val width = holder.view.root.context.screenWidth / 3 - 3

        holder.view.root.layoutParams.height = width
        holder.view.root.layoutParams.width = width

        holder.view.listener = this
        holder.view.width = width
        holder.view.edge = edge
    }

    override fun onClicked(v: View, shortCode: String) {
        val intent = Intent(v.context, PostDetailsActivity::class.java)
        intent.putExtra("shortCode", shortCode)
        startActivity(v.context, intent, null)
    }
}