package com.sertancanpolat.downloaderforinsta.modules

import com.sertancanpolat.downloaderforinsta.services.InstagramApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object InstagramApiModule {

    @Provides
    @ActivityRetainedScoped
    fun provideInstagramApiService(): InstagramApiService {
        return Retrofit.Builder()
            .baseUrl("https://www.instagram.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(InstagramApiService::class.java)
    }
}