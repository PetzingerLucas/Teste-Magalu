package com.petzinger.magalu.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.petzinger.magalu.data.DataSource
import com.petzinger.magalu.model.pullrequest.PullRequest
import com.petzinger.magalu.model.pullrequest.User
import com.petzinger.magalu.model.repository.RepositoryResponse
import com.petzinger.magalu.ui.repository.RepositoryIntent
import com.petzinger.magalu.ui.repository.RepositoryState
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var dataSource: DataSource

    @Mock
    lateinit var stateObserver: Observer<RepositoryState>

    @InjectMocks
    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `should update state to loading when fetching repositories`() {
        val repositoryResponse = RepositoryResponse("", listOf())
        Mockito.`when`(dataSource.fetchRepositories("kotlin", "stars", 1))
            .thenReturn(Single.just(repositoryResponse))

        viewModel.processIntent(
            RepositoryIntent.LoadRepositories(
                page = 1,
                language = "kotlin",
                sort = "stars"
            )
        )

        val captor = argumentCaptor<RepositoryState>()
        verify(stateObserver, times(3)).onChanged(captor.capture())

        val capturedStates = captor.allValues

        val firstState = capturedStates[0]
        assert(!firstState.isLoading) { "Expected isLoading to be false" }

        val secondState = capturedStates[1]
        assert(secondState.isLoading) { "Expected isLoading to be true" }
        assert(secondState.repositories == repositoryResponse.items) { "Expected repositories to match" }

        val thirdState = capturedStates[2]
        assert(!thirdState.isLoading)
    }

    @Test
    fun `should update state to error when fetching repositories fails`() {
        val errorMessage = "Network error"
        Mockito.`when`(dataSource.fetchRepositories("kotlin", "stars", 1))
            .thenReturn(Single.error(Throwable(errorMessage)))

        viewModel.processIntent(
            RepositoryIntent.LoadRepositories(
                page = 1,
                language = "kotlin",
                sort = "stars"
            )
        )

        verify(stateObserver).onChanged(RepositoryState(isLoading = true))
        verify(stateObserver).onChanged(RepositoryState(isLoading = false, error = errorMessage))
    }

    @Test
    fun `should update state to loading when fetching pull requests`() {
        val pullRequests = listOf(
            PullRequest(
                id = 1,
                title = "PR 1",
                body = "test body",
                createdAt = "test2",
                user = User("Teste", "url.com"),
                url = "http://example.com/1"
            ),
            PullRequest(
                id = 2,
                title = "PR 2",
                body = "test body 2",
                createdAt = "test",
                user = User("Teste2", "url2.com"),
                url = "http://example.com/2"
            )
        )
        Mockito.`when`(dataSource.fetchPullRequests("owner", "repo"))
            .thenReturn(Single.just(pullRequests))

        viewModel.processIntent(
            RepositoryIntent.LoadPullRequests(
                owner = "owner",
                repo = "repo"
            )
        )

        val captor = argumentCaptor<RepositoryState>()
        verify(stateObserver, times(3)).onChanged(captor.capture())

        val capturedStates = captor.allValues

        val firstState = capturedStates[0]
        assert(!firstState.isLoading) { "Expected isLoading to be false" }

        val secondState = capturedStates[1]
        assert(secondState.isLoading) { "Expected isLoading to be true" }

        val thirdState = capturedStates[2]
        assert(!thirdState.isLoading) { "Expected isLoading to be false" }
        assert(thirdState.pullRequests == pullRequests) { "Expected pullRequests to match" }
    }

    @Test
    fun `should update state to error when fetching pull requests fails`() {
        val errorMessage = "Network error"
        Mockito.`when`(dataSource.fetchPullRequests("owner", "repo"))
            .thenReturn(Single.error(Throwable(errorMessage)))

        viewModel.processIntent(
            RepositoryIntent.LoadPullRequests(
                owner = "owner",
                repo = "repo"
            )
        )

        verify(stateObserver).onChanged(RepositoryState(isLoading = true))
        verify(stateObserver).onChanged(RepositoryState(isLoading = false, error = errorMessage))
    }
}
