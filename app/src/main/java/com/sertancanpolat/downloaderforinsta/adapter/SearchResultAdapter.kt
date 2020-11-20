package com.sertancanpolat.downloaderforinsta.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.databinding.SraItemRowBinding
import com.sertancanpolat.downloaderforinsta.model.SearchedUserModel
import com.sertancanpolat.downloaderforinsta.view.UserActivity

class SearchResultAdapter(private val result: SearchedUserModel) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultActivityViewHolder>(), SRAItem {

    inner class SearchResultActivityViewHolder(var view: SraItemRowBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(user: SearchedUserModel.UserWithPosition.User?){
            view.listener = this@SearchResultAdapter
            view.userModel = user
        }
    }

    override fun getItemCount(): Int = result.users?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SraItemRowBinding>(inflater, R.layout.sra_item_row, parent, false)
        return SearchResultActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultActivityViewHolder, position: Int) {
        holder.bind(result.users!![position].user)
    }

    override fun onClicked(v: View, user: SearchedUserModel.UserWithPosition.User) {
        val intent = Intent(v.context, UserActivity::class.java)
        intent.putExtra("userName", user.username)
        startActivity(v.context, intent, null)
    }
}


