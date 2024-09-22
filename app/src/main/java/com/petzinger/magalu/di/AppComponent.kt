package com.petzinger.magalu.di

import com.petzinger.magalu.ui.MainActivity
import com.petzinger.magalu.ui.fragment.PullRequestListFragment
import com.petzinger.magalu.ui.fragment.RepositoryListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(repositoryListFragment: RepositoryListFragment)
    fun inject(repositoryListFragment: PullRequestListFragment)
}

