package com.fossil.stackoverflowuser.di.reputation

import com.fossil.stackoverflowuser.modules.reputation.view.ReputationFragment
import dagger.Subcomponent

@ReputationScope
@Subcomponent(modules = [ReputationModule::class])
interface ReputationComponent {
    fun inject(fragment : ReputationFragment)
}