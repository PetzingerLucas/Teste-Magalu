package com.petzinger.magalu.di

import com.petzinger.magalu.MainActivity
import com.petzinger.magalu.ui.RepositoryListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(repositoryListFragment: RepositoryListFragment)
}

