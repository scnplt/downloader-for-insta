package com.sertancanpolat.downloaderforinsta.data_binding_listeners

import android.view.View
import com.sertancanpolat.downloaderforinsta.model.SearchedUserModel

interface SRAItemListener {
    fun onClicked(v: View, user: SearchedUserModel.UserWithPosition.User)
}