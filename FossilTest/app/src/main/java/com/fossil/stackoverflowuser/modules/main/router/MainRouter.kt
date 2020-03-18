package com.fossil.stackoverflowuser.modules.main.router

import androidx.appcompat.app.AppCompatActivity
import com.fossil.stackoverflowuser.R
import com.fossil.stackoverflowuser.modules.main.MainContract
import com.fossil.stackoverflowuser.modules.userlist.view.UserListFragment

class MainRouter(var activity: AppCompatActivity?) : MainContract.Router {

    /**
     * Router to User List View
     */
    override fun showUserListView() {
        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(R.id.main_container, UserListFragment())
        ft?.commit()
    }
}