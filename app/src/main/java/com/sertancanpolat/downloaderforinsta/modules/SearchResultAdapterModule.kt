package com.sertancanpolat.downloaderforinsta.modules

import com.sertancanpolat.downloaderforinsta.models.SearchedUserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object SearchResultAdapterModule {

    @Provides
    @ActivityScoped
    fun provideSearchedUserModel() : SearchedUserModel {
        return SearchedUserModel()
    }
}