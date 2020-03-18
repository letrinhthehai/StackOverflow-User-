package com.fossil.stackoverflowuser.modules.userlist.router

import com.fossil.stackoverflowuser.R
import com.fossil.stackoverflowuser.modules.bookmark.view.BookmarkFragment
import com.fossil.stackoverflowuser.modules.reputation.view.ReputationFragment
import com.fossil.stackoverflowuser.modules.userlist.UserListContract
import com.fossil.stackoverflowuser.modules.userlist.view.UserListFragment
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