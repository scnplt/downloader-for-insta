package com.sertancanpolat.downloaderforinsta.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sertancanpolat.downloaderforinsta.api.RetrofitBuilder
import com.sertancanpolat.downloaderforinsta.model.PostModel
import com.sertancanpolat.downloaderforinsta.utilities.ProcessState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PostDetailsViewModel : ViewModel() {
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
            RetrofitBuilder.api.getPost(shortCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PostModel>() {
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