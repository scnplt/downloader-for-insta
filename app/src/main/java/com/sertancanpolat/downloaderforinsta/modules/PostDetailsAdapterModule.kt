package com.sertancanpolat.downloaderforinsta.modules

import com.sertancanpolat.downloaderforinsta.models.PostModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object PostDetailsAdapterModule {

    @Provides
    @ActivityScoped
    fun providePostModel(): PostModel {
        return PostModel()
    }
}
