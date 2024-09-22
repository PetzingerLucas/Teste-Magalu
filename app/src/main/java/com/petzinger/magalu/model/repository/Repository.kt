package com.petzinger.magalu.model.repository

import android.util.Log
import com.petzinger.magalu.model.PullRequest
import com.petzinger.magalu.model.RepositoryResponse
import com.petzinger.magalu.network.GitHubApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: GitHubApi) {

    fun fetchRepositories(language: String, sort: String, page: Int): Single<RepositoryResponse> {
        Log.d("Repository", "Starting fetchRepositories request")
        return apiService.getRepositories(language, sort, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { Log.d("Repository", "Request started") }
            .doOnSuccess { Log.d("Repository", "Request succeeded with response: $it") }
            .doOnError { Log.e("Repository", "Request failed with error: ${it.message}") }
    }

    fun fetchPullRequests(owner: String, repo: String): Single<List<PullRequest>> {
        return apiService.getPullRequests(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}