package com.sertancanpolat.downloaderforinsta.modules

import com.sertancanpolat.downloaderforinsta.models.UserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UserPostAdapterModule {

    @Provides
    @ActivityScoped
    fun provideUserModel() : UserModel.Graphql.User {
        return UserModel.Graphql.User()
    }
}
