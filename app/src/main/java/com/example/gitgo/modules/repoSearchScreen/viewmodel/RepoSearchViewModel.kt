package com.example.gitgo.modules.repoSearchScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoSearchViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubRepoRepository
) : ViewModel() {
     val repos = MutableStateFlow<PagingData<GitHubSearchRepoModel.Item>>(PagingData.empty())
    fun searchRepositories(queryMap: HashMap<String, String>) {
        viewModelScope.launch {
                gitHubSearchRepository.searchRepositories(queryMap).flow.cachedIn(viewModelScope).collectLatest {
                    repos.value = it
                }
        }
    }
}