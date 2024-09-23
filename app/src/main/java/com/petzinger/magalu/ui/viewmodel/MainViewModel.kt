package com.petzinger.magalu.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petzinger.magalu.model.pullrequest.PullRequest
import com.petzinger.magalu.model.repository.RepositoryResponse
import com.petzinger.magalu.data.DataSource
import com.petzinger.magalu.ui.repository.RepositoryIntent
import com.petzinger.magalu.ui.repository.RepositoryState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<RepositoryState>()
    val state: LiveData<RepositoryState> get() = _state
    var currentPage: Int = 1

    init {
        _state.value = RepositoryState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun processIntent(intent: RepositoryIntent) {
        when (intent) {
            is RepositoryIntent.LoadRepositories -> {
                currentPage = if (intent.page != 1) intent.page else currentPage
                fetchRepositories(
                    intent.language,
                    intent.sort,
                    currentPage
                )
            }

            is RepositoryIntent.LoadPullRequests -> fetchPullRequests(intent.owner, intent.repo)
        }
    }

    private fun fetchRepositories(language: String, sort: String, page: Int) {
        if(_state.value?.isLoading == true) return
        _state.value = _state.value?.copy(isLoading = true)
        val disposable = dataSource.fetchRepositories(language, sort, page)
            .subscribe(::onGetRepoSuccess, ::onError)

        compositeDisposable.add(disposable)
    }

    private fun fetchPullRequests(owner: String, repo: String) {
        _state.value = (_state.value?.copy(isLoading = true))
        val disposable = dataSource.fetchPullRequests(owner, repo)
            .subscribe(::onGetPrSuccess, ::onError)

        compositeDisposable.add(disposable)
    }

    private fun onGetRepoSuccess(response: RepositoryResponse) {
        val currentList = _state.value?.repositories.orEmpty()
        _state.value = _state.value?.copy(
            isLoading = false,
            repositories = currentList + response.items
        )
        currentPage++

    }

    private fun onGetPrSuccess(response: List<PullRequest>) {
        _state.value = _state.value?.copy(
            isLoading = false,
            pullRequests = response
        )
    }

    private fun onError(error: Throwable) {
        _state.value = _state.value?.copy(isLoading = false, error = error.message)
    }
}