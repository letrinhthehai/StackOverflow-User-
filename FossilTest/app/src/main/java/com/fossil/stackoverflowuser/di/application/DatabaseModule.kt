package com.fossil.stackoverflowuser.di.application

import android.content.Context
import com.fossil.stackoverflowuser.database.AppDatabase
import com.fossil.stackoverflowuser.database.HandlerWorkerThread
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@Named("ApplicationContext") context: Context): AppDatabase? =
        AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideWorkerThread(): HandlerWorkerThread = HandlerWorkerThread("dbWorkThread")

}