package com.petzinger.magalu.di

import com.petzinger.magalu.data.DataSource
import com.petzinger.magalu.network.GitHubApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDataSource(apiService: GitHubApi): DataSource {
        return DataSource(apiService)
    }
}
