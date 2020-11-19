package com.sertancanpolat.downloaderforinsta.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertancanpolat.downloaderforinsta.viewmodel.UserViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}