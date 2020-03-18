package com.fossil.stackoverflowuser.di.reputation

import com.fossil.stackoverflowuser.modules.reputation.interactor.ReputationInteractor
import com.fossil.stackoverflowuser.modules.reputation.presenter.ReputationPresenter
import com.fossil.stackoverflowuser.modules.reputation.view.ReputationFragment
import com.fossil.stackoverflowuser.services.StackExchangeServices
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ReputationModule (private val fragment: ReputationFragment){

    @Provides
    @ReputationScope
    fun provideReputationPresenter(
        interactor: ReputationInteractor?
    ) : ReputationPresenter = ReputationPresenter(interactor)

    @Provides
    @ReputationScope
    fun provideReputationInteractor(
        services: StackExchangeServices,
        compositeDisposable: CompositeDisposable
    ): ReputationInteractor = ReputationInteractor(services, compositeDisposable)

    @Provides
    @ReputationScope
    fun provideUserListCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}