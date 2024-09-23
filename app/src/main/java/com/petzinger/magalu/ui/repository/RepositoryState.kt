package com.petzinger.magalu.ui.repository

import com.petzinger.magalu.model.pullrequest.PullRequest
import com.petzinger.magalu.model.repository.RepositoryItem

data class RepositoryState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryItem> = emptyList(),
    val pullRequests: List<PullRequest> = emptyList(),
    val error: String? = null,
)