package com.example.gitgo.application.di

import com.example.gitgo.components.network.apis.GitHubApi
import com.example.gitgo.components.network.apis.login.LoginApi
import com.example.gitgo.components.network.repositories.implementation.GitHubRepoRepositoryImpl
import com.example.gitgo.components.network.repositories.implementation.loginRepoImplemetation.LoginRepositoryImplementation
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.components.network.repositories.interfaces.loginRepo.LoginRepository
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
    fun provideGitHubSearchRepository(gitHubApi: GitHubApi): GitHubRepoRepository {
        return GitHubRepoRepositoryImpl(gitHubApi)
    }

    @Singleton
    @Provides
    fun providesLoginRepository(loginApi: LoginApi): LoginRepository {
        return LoginRepositoryImplementation(loginApi = loginApi)
    }
}