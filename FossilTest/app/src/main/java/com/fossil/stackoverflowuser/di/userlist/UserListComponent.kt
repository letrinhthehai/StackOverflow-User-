package com.fossil.stackoverflowuser.di.userlist

import com.fossil.stackoverflowuser.modules.userlist.view.UserListFragment
import dagger.Subcomponent

@UserListScope
@Subcomponent(modules = [UserListModule::class])
interface UserListComponent {
    fun inject(fragment: UserListFragment)
}