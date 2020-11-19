package com.sertancanpolat.downloaderforinsta.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertancanpolat.downloaderforinsta.viewmodel.PostDetailsViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class PostDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostDetailsViewModel::class.java))
            return PostDetailsViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}