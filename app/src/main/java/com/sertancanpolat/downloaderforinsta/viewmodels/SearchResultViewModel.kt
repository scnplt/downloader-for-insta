package com.sertancanpolat.downloaderforinsta.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sertancanpolat.downloaderforinsta.models.SearchedUserModel
import com.sertancanpolat.downloaderforinsta.services.InstagramApiService
import com.sertancanpolat.downloaderforinsta.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val instagramApiService: InstagramApiService) :
    ViewModel() {

    val searchResult = MutableLiveData<SearchedUserModel>()
    val processState = MutableLiveData<ProcessState>()

    private val disposable = CompositeDisposable()

    init {
        searchResult.value = null
        processState.value = ProcessState.IDLE
    }

    fun clear() = disposable.clear()

    fun searchUser(userName: String) {
        processState.value = ProcessState.LOADING
        disposable.add(
            instagramApiService.searchUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchedUserModel>() {
                    override fun onSuccess(t: SearchedUserModel) {
                        searchResult.value = t
                        processState.value = ProcessState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        println("error: ${e.localizedMessage}")
                        processState.value = ProcessState.ERROR
                    }
                })
        )
    }
}