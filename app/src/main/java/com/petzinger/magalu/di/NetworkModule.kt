package com.petzinger.magalu.di

import com.petzinger.magalu.BuildConfig
import com.petzinger.magalu.network.GitHubApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val token = System.getenv(GITHUB_API_TOKEN) ?: BuildConfig.GITHUB_API_TOKEN
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
//            .addInterceptor(AuthInterceptor(token))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)


    private companion object {
        const val GITHUB_API_TOKEN = "GIT_HUB_API_TOKEN"
    }
}
