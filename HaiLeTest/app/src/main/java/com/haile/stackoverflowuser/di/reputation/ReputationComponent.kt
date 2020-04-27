package com.haile.stackoverflowuser.di.reputation

import com.haile.stackoverflowuser.modules.reputation.view.ReputationFragment
import dagger.Subcomponent

@ReputationScope
@Subcomponent(modules = [ReputationModule::class])
interface ReputationComponent {
    fun inject(fragment : ReputationFragment)
}