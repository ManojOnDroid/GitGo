package com.example.gitgo.modules.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
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

    init {
        fetchTrending()
    }

    fun fetchTrending(
        periodDays: Int = 7,
        language: String? = null
    ) {
        viewModelScope.launch {
            _state.value = TrendingReposState.Loading
            try {
                val query = buildTrendingQuery(periodDays, language)

                val queryMap = hashMapOf(
                    "q" to query,
                    "sort" to "stars",
                    "order" to "desc"
                )

                val flow = gitHubSearchRepository.searchRepositories(queryMap)
                    .flow
                    .cachedIn(viewModelScope)

                _state.value = TrendingReposState.Success(flow)
            } catch (e: Exception) {
                _state.value = TrendingReposState.Error(
                    e.localizedMessage ?: "Failed to fetch trending repositories"
                )
            }
        }
    }

    private fun buildTrendingQuery(periodDays: Int, language: String?): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -periodDays)
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(calendar.time)

        return buildString {
            append("created:>$formattedDate")
            if (!language.isNullOrBlank()) {
                append(" language:$language")
            }
        }
    }
}