package com.petzinger.magalu.di

import com.petzinger.magalu.model.repository.Repository
import com.petzinger.magalu.network.GitHubApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: GitHubApi): Repository {
        return Repository(apiService)
    }
}
