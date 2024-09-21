package com.petzinger.magalu.ui

sealed class RepositoryIntent {
    data object LoadRepositories : RepositoryIntent()
    data class LoadPullRequests(val owner: String, val repo: String) : RepositoryIntent()
}