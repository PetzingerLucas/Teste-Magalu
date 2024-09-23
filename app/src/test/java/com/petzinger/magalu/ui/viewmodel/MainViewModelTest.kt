package com.petzinger.magalu.ui.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.petzinger.magalu.data.DataSource
import com.petzinger.magalu.data.DataSourceImpl
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
import org.mockito.junit.MockitoJUnitRunner

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
        // Arrange
        val repositoryResponse = RepositoryResponse("", listOf())
        Mockito.`when`(dataSource.fetchRepositories("kotlin", "stars", 1))
            .thenReturn(Single.just(repositoryResponse))

        // Act
        viewModel.processIntent(RepositoryIntent.LoadRepositories(page = 1, language = "kotlin", sort= "stars"))

        // Assert
        Mockito.verify(stateObserver).onChanged(RepositoryState(isLoading = true))
        Mockito.verify(stateObserver).onChanged(RepositoryState(isLoading = false, repositories = repositoryResponse.items))
    }

    @Test
    fun `should update state to error when fetching repositories fails`() {
        // Arrange
        val errorMessage = "Network error"
        Mockito.`when`(dataSource.fetchRepositories("kotlin", "stars", 1))
            .thenReturn(Single.error(Throwable(errorMessage)))

        // Act
        viewModel.processIntent(RepositoryIntent.LoadRepositories(page = 1, language = "kotlin", sort= "stars"))

        // Assert
        Mockito.verify(stateObserver).onChanged(RepositoryState(isLoading = true))
        Mockito.verify(stateObserver).onChanged(RepositoryState(isLoading = false, error = errorMessage))
    }
}
