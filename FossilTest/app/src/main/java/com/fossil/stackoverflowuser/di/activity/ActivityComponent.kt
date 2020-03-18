package com.fossil.stackoverflowuser.di.activity

import com.fossil.stackoverflowuser.di.bookmark.BookmarkComponent
import com.fossil.stackoverflowuser.di.bookmark.BookmarkModule
import com.fossil.stackoverflowuser.di.reputation.ReputationComponent
import com.fossil.stackoverflowuser.di.reputation.ReputationModule
import com.fossil.stackoverflowuser.di.userlist.UserListComponent
import com.fossil.stackoverflowuser.di.userlist.UserListModule
import com.fossil.stackoverflowuser.modules.main.view.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun plusUserListComponent(userListModule: UserListModule) : UserListComponent

    fun plusReputationComponent(reputationModule: ReputationModule): ReputationComponent

    fun plusBookmarkComponent(bookmarkModule: BookmarkModule) : BookmarkComponent
}