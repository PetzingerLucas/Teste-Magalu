package com.petzinger.magalu.data

import com.petzinger.magalu.model.pullrequest.PullRequest
import com.petzinger.magalu.model.repository.RepositoryResponse
import com.petzinger.magalu.network.GitHubApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val apiService: GitHubApi) : DataSource {

    override fun fetchRepositories(
        language: String,
        sort: String,
        page: Int
    ): Single<RepositoryResponse> =
        apiService.getRepositories(language, sort, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun fetchPullRequests(
        owner: String,
        repo: String
    ): Single<List<PullRequest>> =
        apiService.getPullRequests(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}