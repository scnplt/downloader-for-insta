package com.sertancanpolat.downloaderforinsta.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertancanpolat.downloaderforinsta.viewmodel.SearchResultViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SearchResultViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}