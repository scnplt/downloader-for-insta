package com.sertancanpolat.downloaderforinsta.adapter

import android.view.View
import com.sertancanpolat.downloaderforinsta.model.SearchedUserModel

interface SRAItem {
    fun onClicked(v: View, user: SearchedUserModel.UserWithPosition.User)
}