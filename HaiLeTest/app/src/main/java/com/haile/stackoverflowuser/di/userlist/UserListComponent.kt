package com.haile.stackoverflowuser.di.userlist

import com.haile.stackoverflowuser.modules.userlist.view.UserListFragment
import dagger.Subcomponent

@UserListScope
@Subcomponent(modules = [UserListModule::class])
interface UserListComponent {
    fun inject(fragment: UserListFragment)
}