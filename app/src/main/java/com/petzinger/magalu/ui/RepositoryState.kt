package com.petzinger.magalu.ui

import com.petzinger.magalu.model.PullRequest
import com.petzinger.magalu.model.RepositoryItem

data class RepositoryState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryItem> = emptyList(),
    val pullRequests: List<PullRequest> = emptyList(),
    val error: String? = null,
)