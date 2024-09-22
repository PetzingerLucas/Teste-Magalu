package com.petzinger.magalu.ui

sealed class RepositoryIntent {
    data class LoadRepositories(val page: Int = 1, val sort: String = "stars", val language: String = "kotlin", val isUpdate: Boolean = false) : RepositoryIntent()
    data class LoadPullRequests(val owner: String, val repo: String) : RepositoryIntent()
}