package com.sertancanpolat.downloaderforinsta.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sertancanpolat.downloaderforinsta.models.PostModel
import com.sertancanpolat.downloaderforinsta.services.InstagramApiService
import com.sertancanpolat.downloaderforinsta.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(private val instagramApiService: InstagramApiService) :
    ViewModel() {

    val postModel = MutableLiveData<PostModel>()
    val processState = MutableLiveData<ProcessState>()

    private val disposable = CompositeDisposable()

    init {
        postModel.value = null
        processState.value = ProcessState.IDLE
    }

    fun clear() = disposable.clear()

    fun getPost(shortCode: String) {
        processState.value = ProcessState.LOADING
        disposable.add(
            instagramApiService.getPost(shortCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PostModel>(){
                    override fun onSuccess(t: PostModel) {
                        postModel.value = t
                        processState.value = ProcessState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        processState.value = ProcessState.ERROR
                    }
                })
        )
    }
}