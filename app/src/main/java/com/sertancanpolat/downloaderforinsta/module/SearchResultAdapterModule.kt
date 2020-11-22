package com.sertancanpolat.downloaderforinsta.module

import com.sertancanpolat.downloaderforinsta.model.SearchedUserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class SearchResultAdapterModule {

    @Provides
    @ActivityScoped
    fun provideSearchedUserModel() : SearchedUserModel{
        return SearchedUserModel()
    }
}