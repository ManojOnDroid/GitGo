package com.example.gitgo.modules.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.gitgo.components.network.repositories.interfaces.GitHubRepoRepository
import com.example.gitgo.modules.homeScreen.states.TrendingReposState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubRepoRepository
) : ViewModel() {
    val repos = MutableStateFlow<TrendingReposState>(TrendingReposState.Loading)
    init {
        fetchTrending()
    }
    fun trendingRepositories(queryMap: HashMap<String, String>) {
        queryMap["sort"] = "stars"
        queryMap["order"] = "desc"

        viewModelScope.launch {
            try {
                repos.value = TrendingReposState.Loading
                val flow = gitHubSearchRepository.searchRepositories(queryMap)
                    .flow
                    .cachedIn(viewModelScope)

                repos.value = TrendingReposState.Success(flow)
            } catch (e: Exception) {
                repos.value = TrendingReposState.Error(e.message ?: "Something went wrong")
            }
        }

    }

    fun fetchTrending(
        periodDays: Int = 1,
        language: String? = null,
        useCreated: Boolean = true
    ) {
        val queryMap = hashMapOf<String, String>()

        queryMap["q"] = if (useCreated) {
            trendingQueryCreated(periodDays, language)
        } else {
            trendingQueryPushed(periodDays, language)
        }
        queryMap["sort"] = "stars"
        queryMap["order"] = "desc"
        trendingRepositories(queryMap)
    }

    fun trendingQueryCreated(
        periodDays: Int,
        language: String? = null,
        extra: String? = null
    ): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -periodDays)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        val langPart = language?.let { " language:$it" } ?: ""
        val extraPart = extra?.let { " $it" } ?: ""
        return "created:>$date$langPart$extraPart"
    }

    fun trendingQueryPushed(
        periodDays: Int,
        language: String? = null
    ): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -periodDays)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        val langPart = language?.let { " language:$it" } ?: ""
        return "pushed:>$date$langPart"
    }
}