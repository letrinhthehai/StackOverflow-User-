package com.haile.stackoverflowuser.modules.userlist.router

import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.modules.bookmark.view.BookmarkFragment
import com.haile.stackoverflowuser.modules.reputation.view.ReputationFragment
import com.haile.stackoverflowuser.modules.userlist.UserListContract
import com.haile.stackoverflowuser.modules.userlist.view.UserListFragment
import javax.inject.Inject

class UserListRouter @Inject constructor(private val view: UserListFragment) :
    UserListContract.Router {

    /**
     * Add new reputation fragment
     * Keep current user list fragment data
     */
    fun showReputationView(userId: Int) {
        val fragment = ReputationFragment.newInstance(userId)
        val ft = view.fragmentManager?.beginTransaction()
        ft?.add(R.id.main_container, fragment)
        ft?.addToBackStack(fragment::class.java.simpleName)
        ft?.commit()
    }

    /**
     * Add new Bookmark fragment
     * Clear user list fragment data
     */
    fun showBookmarkView() {
        val fragment = BookmarkFragment()
        val ft = view.fragmentManager?.beginTransaction()
        ft?.replace(R.id.main_container, fragment)
        ft?.addToBackStack(fragment::class.java.simpleName)
        ft?.commit()
    }
}