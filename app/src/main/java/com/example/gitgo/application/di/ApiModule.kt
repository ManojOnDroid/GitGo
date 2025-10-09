package com.example.gitgo.application.di

import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.apis.login.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    @Singleton
    @Provides
    fun providesLoginApi(@Named("AuthRetrofit") retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

}