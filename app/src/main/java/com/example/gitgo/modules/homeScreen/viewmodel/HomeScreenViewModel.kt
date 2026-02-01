package com.example.gitgo.modules.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.homeScreen.models.QuickAction
import com.example.gitgo.modules.homeScreen.states.TrendingReposState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubRepoRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TrendingReposState>(TrendingReposState.Loading)
    val state: StateFlow<TrendingReposState> = _state.asStateFlow()

    private val _currentAction = MutableStateFlow(QuickAction.TRENDING)
    val currentAction: StateFlow<QuickAction> = _currentAction.asStateFlow()

    init {
        fetchRepositories(QuickAction.TRENDING)
    }

    fun onQuickActionSelected(action: QuickAction) {
        if (_currentAction.value != action) {
            _currentAction.value = action
            fetchRepositories(action)
        }
    }

    private fun fetchRepositories(action: QuickAction) {
        viewModelScope.launch {
            _state.value = TrendingReposState.Loading
            try {
                val (query, sort) = buildQueryAndSort(action)

                val queryMap = hashMapOf(
                    "q" to query,
                    "sort" to sort,
                    "order" to "desc"
                )

                val flow = gitHubSearchRepository.searchRepositories(queryMap)
                    .flow
                    .cachedIn(viewModelScope)

                _state.value = TrendingReposState.Success(flow)
            } catch (e: Exception) {
                _state.value = TrendingReposState.Error(
                    e.localizedMessage ?: "Failed to fetch repositories"
                )
            }
        }
    }

    private fun buildQueryAndSort(action: QuickAction): Pair<String, String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        return when (action) {
            QuickAction.TRENDING -> {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val date = dateFormat.format(calendar.time)
                "created:>$date" to "stars"
            }
            QuickAction.POPULAR -> {
                "stars:>10000" to "stars"
            }
            QuickAction.RECENT -> {
                // Recently pushed (last 30 days), Sort by Updated
                calendar.add(Calendar.DAY_OF_YEAR, -30)
                val date = dateFormat.format(calendar.time)
                "pushed:>$date" to "updated"
            }
        }
    }
}