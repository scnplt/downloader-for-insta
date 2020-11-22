package com.sertancanpolat.downloaderforinsta.module

import com.sertancanpolat.downloaderforinsta.model.UserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class UserPostAdapterModule {

    @Provides
    @ActivityScoped
    fun providesUserModel() : UserModel.Graphql.User {
        return UserModel.Graphql.User()
    }
}
