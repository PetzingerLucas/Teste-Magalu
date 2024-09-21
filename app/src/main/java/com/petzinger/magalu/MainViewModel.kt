package com.petzinger.magalu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petzinger.magalu.model.PullRequest
import com.petzinger.magalu.model.RepositoryResponse
import com.petzinger.magalu.model.repository.Repository
import com.petzinger.magalu.ui.RepositoryIntent
import com.petzinger.magalu.ui.RepositoryState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<RepositoryState>()
    val state: LiveData<RepositoryState> get() = _state

    init {
        _state.postValue(RepositoryState())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun processIntent(intent: RepositoryIntent) {
        when (intent) {
            is RepositoryIntent.LoadRepositories -> fetchRepositories()
            is RepositoryIntent.LoadPullRequests -> fetchPullRequests(intent.owner, intent.repo)
        }
    }

    private fun fetchRepositories() {
        _state.value = _state.value?.copy(isLoading = true)
        val disposable = repository.fetchRepositories("kotlin", "stars", 1)
            .subscribe({ onGetRepoSuccess(response = it) }, { onError(error = it) })

        compositeDisposable.add(disposable)
    }

    private fun fetchPullRequests(owner: String, repo: String) {
        _state.postValue(_state.value?.copy(isLoading = true))
        val disposable = repository.fetchPullRequests(owner, repo)
            .subscribe({ onGetPrSuccess(response = it) }, { onError(error = it) })

        compositeDisposable.add(disposable)
    }

    private fun onGetRepoSuccess(response: RepositoryResponse) {
        _state.value = _state.value?.copy(
            isLoading = false,
            repositories = response.items
        )
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