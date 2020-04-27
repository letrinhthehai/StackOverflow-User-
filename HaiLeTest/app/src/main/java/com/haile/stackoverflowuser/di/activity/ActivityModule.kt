package com.haile.stackoverflowuser.di.activity

import android.content.Context
import com.haile.stackoverflowuser.modules.main.presenter.MainPresenter
import com.haile.stackoverflowuser.modules.main.router.MainRouter
import com.haile.stackoverflowuser.modules.main.view.MainActivity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class ActivityModule(private val activity: MainActivity)  {

    @Provides
    @ActivityScope
    @Named("Context")
    fun provideContext(): Context = activity

    @Provides
    @ActivityScope
    fun providerMainPresenter(router: MainRouter?) : MainPresenter = MainPresenter(router)

    @Provides
    @ActivityScope
    @Named("ActivityComposite")
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @ActivityScope
    fun provideMainRouter(): MainRouter = MainRouter(activity)
}