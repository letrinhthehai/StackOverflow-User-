package com.fossil.stackoverflowuser.di.application

import com.fossil.stackoverflowuser.MyApplication
import com.fossil.stackoverflowuser.di.activity.ActivityComponent
import com.fossil.stackoverflowuser.di.activity.ActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject (application: MyApplication)
    fun plusActivityComponent(activityModule : ActivityModule): ActivityComponent
}