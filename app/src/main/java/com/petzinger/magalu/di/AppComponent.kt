package com.petzinger.magalu.di

import com.petzinger.magalu.ui.MainActivity
import com.petzinger.magalu.ui.pullrequest.PullRequestListFragment
import com.petzinger.magalu.ui.repository.RepositoryListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(repositoryListFragment: RepositoryListFragment)
    fun inject(repositoryListFragment: PullRequestListFragment)
}

