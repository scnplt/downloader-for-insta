package com.sertancanpolat.downloaderforinsta.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sertancanpolat.downloaderforinsta.models.UserDataModel
import com.sertancanpolat.downloaderforinsta.models.UserModel
import com.sertancanpolat.downloaderforinsta.services.InstagramApiService
import com.sertancanpolat.downloaderforinsta.utils.ProcessState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserViewModel @ViewModelInject constructor(private val instagramApiService: InstagramApiService) :
    ViewModel() {

    val userModel = MutableLiveData<UserModel>()
    val incomingMediaSize = MutableLiveData<Int>()
    val lastMediaIndex = MutableLiveData<Int>()
    val userState = MutableLiveData<ProcessState>()
    val morePostState = MutableLiveData<ProcessState>()

    private val disposable = CompositeDisposable()

    init {
        userState.value = ProcessState.IDLE
        morePostState.value = ProcessState.IDLE
        userModel.value = null
        incomingMediaSize.value = 0
        lastMediaIndex.value = 0
    }

    fun clear() = disposable.clear()

    fun getUser(userName: String) {
        userState.value = ProcessState.LOADING
        disposable.add(
            instagramApiService.getUser(userName)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(t: UserModel) {
                        userModel.value = t
                        userState.value = ProcessState.LOADED
                        lastMediaIndex.value = t.graphql?.user?.edgeOwnerToTimelineMedia?.edges?.lastIndex ?: 0
                    }

                    override fun onError(e: Throwable) {
                        userState.value = ProcessState.ERROR
                    }
                })
        )
    }

    fun getMorePost(id: String, endCursor: String?) {
        morePostState.value = ProcessState.LOADING
        val query = "{\"id\":\"$id\", \"first\":80, \"after\":\"$endCursor\"}"
        disposable.add(
            instagramApiService.getData(query)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserDataModel>() {
                    override fun onSuccess(t: UserDataModel) {
                        incomingMediaSize.value = t.data?.user?.edgeOwnerToTimelineMedia?.edges?.size ?: 0
                        userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.pageInfo = t.data?.user?.edgeOwnerToTimelineMedia?.pageInfo
                        userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.edges?.addAll(t.data?.user?.edgeOwnerToTimelineMedia?.edges!!)
                        lastMediaIndex.value = userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.edges?.size!! - 1
                        morePostState.value = ProcessState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        morePostState.value = ProcessState.ERROR
                    }
                })
        )
    }
}