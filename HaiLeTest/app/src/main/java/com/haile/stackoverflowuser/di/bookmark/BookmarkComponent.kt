package com.haile.stackoverflowuser.di.bookmark

import com.haile.stackoverflowuser.modules.bookmark.view.BookmarkFragment
import dagger.Subcomponent

@BookmarkScope
@Subcomponent(modules = [BookmarkModule::class])
interface BookmarkComponent {
    fun inject(fragment: BookmarkFragment)
}