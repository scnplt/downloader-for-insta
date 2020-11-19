package com.sertancanpolat.downloaderforinsta.api

import com.sertancanpolat.downloaderforinsta.model.PostModel
import com.sertancanpolat.downloaderforinsta.model.SearchedUserModel
import com.sertancanpolat.downloaderforinsta.model.UserDataModel
import com.sertancanpolat.downloaderforinsta.model.UserModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InstagramApi {
    @GET("{userName}/?__a=1")
    fun getUser(@Path("userName") userName: String): Single<UserModel>

    @GET("p/{shortCode}/?__a=1")
    fun getPost(@Path("shortCode") shortCode: String): Single<PostModel>

    @GET("graphql/query/?query_id=17888483320059182")
    fun getData(@Query("variables") query: String): Single<UserDataModel>

    @GET("web/search/topsearch/?")
    fun searchUser(@Query("query") userName: String): Single<SearchedUserModel>
}