package com.petzinger.magalu.di

import com.petzinger.magalu.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}

