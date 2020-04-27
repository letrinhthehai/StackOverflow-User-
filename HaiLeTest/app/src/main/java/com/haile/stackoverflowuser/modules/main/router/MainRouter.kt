package com.haile.stackoverflowuser.modules.main.router

import androidx.appcompat.app.AppCompatActivity
import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.modules.main.MainContract
import com.haile.stackoverflowuser.modules.userlist.view.UserListFragment

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