package com.sertancanpolat.downloaderforinsta.adapters

import android.animation.ValueAnimator
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.activities.UserActivity
import com.sertancanpolat.downloaderforinsta.databinding.SraItemRowBinding
import com.sertancanpolat.downloaderforinsta.interfaces.SRAItemListener
import com.sertancanpolat.downloaderforinsta.models.SearchedUserModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SearchResultAdapter @Inject constructor(var searchedUserModel: SearchedUserModel) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultActivityViewHolder>(), SRAItemListener {

    class SearchResultActivityViewHolder(var view: SraItemRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun getItemCount(): Int = searchedUserModel.users?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SraItemRowBinding>(inflater, R.layout.sra_item_row, parent, false)
        return SearchResultActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultActivityViewHolder, position: Int) {
        holder.view.listener = this
        holder.view.userModel = searchedUserModel.users!![position].user

        val animator = ValueAnimator.ofFloat(0.85f, 1f).apply { duration = 400 }
        animator.start()

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            holder.view.root.scaleX = value
            holder.view.root.scaleY = value
        }
    }

    override fun onClicked(v: View, user: SearchedUserModel.UserWithPosition.User) {
        val intent = Intent(v.context, UserActivity::class.java)
        intent.putExtra("userName", user.username)
        startActivity(v.context, intent, null)
    }
}


