package com.sertancanpolat.downloaderforinsta.interfaces

import android.view.View
import com.sertancanpolat.downloaderforinsta.models.SearchedUserModel

interface SRAItemListener {
    fun onClicked(v: View, user: SearchedUserModel.UserWithPosition.User)
}