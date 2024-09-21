package com.petzinger.magalu.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petzinger.magalu.MainViewModel
import com.petzinger.magalu.ViewModelFactory
import com.petzinger.magalu.model.repository.Repository
import com.petzinger.magalu.network.GitHubApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
 class AppModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: GitHubApi): Repository {
        return Repository(apiService)
    }
}
