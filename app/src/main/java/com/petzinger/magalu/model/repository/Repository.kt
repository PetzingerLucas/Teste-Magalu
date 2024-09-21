package com.petzinger.magalu.model.repository

import com.petzinger.magalu.model.PullRequest
import com.petzinger.magalu.model.RepositoryResponse
import com.petzinger.magalu.network.GitHubApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: GitHubApi) {

    fun fetchRepositories(language: String, sort: String, page: Int): Single<RepositoryResponse> {
        return apiService.getRepositories(language, sort, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchPullRequests(owner: String, repo: String): Single<List<PullRequest>> {
        return apiService.getPullRequests(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}