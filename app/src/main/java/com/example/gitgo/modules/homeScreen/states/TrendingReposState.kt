package com.example.gitgo.modules.homeScreen.states

import androidx.paging.PagingData
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import kotlinx.coroutines.flow.Flow

sealed class TrendingReposState {
    object Loading : TrendingReposState()
    data class Success(val items: Flow<PagingData<GitHubSearchRepoModel.Item>>) : TrendingReposState()
    data class Error(val message: String) : TrendingReposState()
}