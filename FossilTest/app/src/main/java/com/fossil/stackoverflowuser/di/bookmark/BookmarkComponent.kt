package com.fossil.stackoverflowuser.di.bookmark

import com.fossil.stackoverflowuser.modules.bookmark.view.BookmarkFragment
import dagger.Subcomponent

@BookmarkScope
@Subcomponent(modules = [BookmarkModule::class])
interface BookmarkComponent {
    fun inject(fragment: BookmarkFragment)
}