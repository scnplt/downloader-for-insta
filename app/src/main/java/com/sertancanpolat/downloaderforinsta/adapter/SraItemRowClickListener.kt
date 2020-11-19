package com.sertancanpolat.downloaderforinsta.adapter

import android.view.View
import com.sertancanpolat.downloaderforinsta.model.SUMUserUser

interface SraItemRowClickListener {
    fun onSraItemRowClicked(v: View, user: SUMUserUser)
}