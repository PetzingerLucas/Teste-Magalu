package com.petzinger.magalu.data

import com.petzinger.magalu.model.pullrequest.PullRequest
import com.petzinger.magalu.model.repository.RepositoryResponse
import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun fetchRepositories(language: String, sort: String, page: Int): Single<RepositoryResponse>

    fun fetchPullRequests(owner: String, repo: String): Single<List<PullRequest>>
}