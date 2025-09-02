package com.example.gitgo.application.di

import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.repositories.implementation.GitHubSearchRepositoryImpl
import com.example.gitgo.components.network.repositories.interfaces.GitHubSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideGitHubSearchRepository(gitHubApi: GitHubApi): GitHubSearchRepository {
        return GitHubSearchRepositoryImpl(gitHubApi)
    }
}