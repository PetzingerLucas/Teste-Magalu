package com.petzinger.magalu.ui.repository

sealed class RepositoryIntent {
    data class LoadRepositories(val page: Int = 1, val sort: String = "stars", val language: String = "kotlin") : RepositoryIntent()
    data class LoadPullRequests(val owner: String, val repo: String) : RepositoryIntent()
}