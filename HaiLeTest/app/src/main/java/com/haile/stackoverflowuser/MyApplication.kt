package com.haile.stackoverflowuser

import android.app.Application
import com.haile.stackoverflowuser.di.application.AppComponent
import com.haile.stackoverflowuser.di.application.AppModule
import com.haile.stackoverflowuser.di.activity.ActivityComponent
import com.haile.stackoverflowuser.di.activity.ActivityModule
import com.haile.stackoverflowuser.di.application.DaggerAppComponent
import com.haile.stackoverflowuser.modules.main.view.MainActivity

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
