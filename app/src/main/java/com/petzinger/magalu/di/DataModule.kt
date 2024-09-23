package com.petzinger.magalu.di

import com.petzinger.magalu.data.DataSource
import com.petzinger.magalu.data.DataSourceImpl
import com.petzinger.magalu.network.GitHubApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDataSource(apiService: GitHubApi): DataSourceImpl {
        return DataSourceImpl(apiService)
    }
}

@Module
abstract class DataModuleBind {
    @Binds
    @Singleton
    abstract fun bindDataSource(dataSourceImpl: DataSourceImpl): DataSource
}

