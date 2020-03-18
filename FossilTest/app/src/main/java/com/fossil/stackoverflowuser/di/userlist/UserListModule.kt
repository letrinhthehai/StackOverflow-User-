package com.fossil.stackoverflowuser.di.userlist

import com.fossil.stackoverflowuser.modules.userlist.interactor.UserListInteractor
import com.fossil.stackoverflowuser.modules.userlist.presenter.UserListPresenter
import com.fossil.stackoverflowuser.modules.userlist.router.UserListRouter
import com.fossil.stackoverflowuser.modules.userlist.view.UserListFragment
import com.fossil.stackoverflowuser.services.StackExchangeServices
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class UserListModule(private val fragment: UserListFragment) {
    @Provides
    @UserListScope
    fun provideUserListPresenter(
        interactor: UserListInteractor?,
        router: UserListRouter?
    ): UserListPresenter = UserListPresenter(interactor, router)

    @Provides
    @UserListScope
    fun provideUserListInteractor(
        services: StackExchangeServices,
        compositeDisposable: CompositeDisposable
    ): UserListInteractor = UserListInteractor(services, compositeDisposable)

    @Provides
    @UserListScope
    fun provideUserListCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @UserListScope
    fun provideUserListRouter() = UserListRouter(fragment)
}