package com.haile.stackoverflowuser.di.application

import com.haile.stackoverflowuser.MyApplication
import com.haile.stackoverflowuser.di.activity.ActivityComponent
import com.haile.stackoverflowuser.di.activity.ActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject (application: MyApplication)
    fun plusActivityComponent(activityModule : ActivityModule): ActivityComponent
}