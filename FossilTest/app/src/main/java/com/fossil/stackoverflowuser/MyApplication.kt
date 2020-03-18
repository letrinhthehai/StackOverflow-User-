package com.fossil.stackoverflowuser

import android.app.Application
import com.fossil.stackoverflowuser.di.application.AppComponent
import com.fossil.stackoverflowuser.di.application.AppModule
import com.fossil.stackoverflowuser.di.activity.ActivityComponent
import com.fossil.stackoverflowuser.di.activity.ActivityModule
import com.fossil.stackoverflowuser.di.application.DaggerAppComponent
import com.fossil.stackoverflowuser.modules.main.view.MainActivity

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent
    var activityComponent: ActivityComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = initAppComponent(this)
    }

    private fun initAppComponent(app: MyApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    fun plusActivityComponent(activity: MainActivity): ActivityComponent? {
        if (activityComponent == null) {
            activityComponent = appComponent.plusActivityComponent(ActivityModule(activity))
        }
        return activityComponent
    }

    fun clearActivityComponent() {
        activityComponent = null
    }
}
