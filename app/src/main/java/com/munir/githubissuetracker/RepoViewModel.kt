package com.munir.githubissuetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.munir.githubissuetracker.graphql.GetIssuesQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoViewModel(private val apolloClient: ApolloClient) : ViewModel() {

    private val _uiState = MutableStateFlow<RepoUiState>(RepoUiState.Idle)
    val uiState: StateFlow<RepoUiState> = _uiState

    fun fetchIssues(owner: String, repo: String) {
        _uiState.value = RepoUiState.Loading
        viewModelScope.launch {
            try {
                val response = apolloClient.query(GetIssuesQuery(owner, repo)).execute()
                val issues = response.data?.repository?.issues?.nodes?.filterNotNull() // Remove nulls

                if (!issues.isNullOrEmpty()) {
                    _uiState.value = RepoUiState.Success(issues.map {
                        IssueItem(
                            it.id ?: "Unknown ID", // Ensure ID is not null
                            it.title ?: "No Title",
                            it.body ?: "No description"
                        )
                    })
                } else {
                    _uiState.value = RepoUiState.Error("No issues found.")
                }

            } catch (e: ApolloException) {
                _uiState.value = RepoUiState.Error("Failed to fetch issues: ${e.message}")
            }
        }
    }
}

// UI State Sealed Class
sealed class RepoUiState {
    object Idle : RepoUiState()
    object Loading : RepoUiState()
    data class Success(val issues: List<IssueItem>) : RepoUiState()
    data class Error(val message: String) : RepoUiState()
}

// Data Class for Issue Item
data class IssueItem(val id: String, val title: String, val body: String)
