package com.example.gitgo.modules.repoSearchScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitgo.components.network.models.GitHubSearchRepoModel
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class RepoSearchViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubRepoRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val repos: Flow<PagingData<GitHubSearchRepoModel.Item>> = _query
        .debounce(500)
        .flatMapLatest { queryStr ->
            if (queryStr.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                val queryMap = hashMapOf("q" to queryStr, "sort" to "stars")
                gitHubSearchRepository.searchRepositories(queryMap).flow
            }
        }
        .cachedIn(viewModelScope)

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }
}