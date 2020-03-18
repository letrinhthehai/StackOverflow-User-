package com.fossil.stackoverflowuser.di.application

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    @Named("ApplicationContext")
    fun provideContext() : Context = app
}