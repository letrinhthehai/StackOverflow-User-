package com.haile.stackoverflowuser.modules.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.haile.stackoverflowuser.MyApplication
import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.database.AppDatabase
import com.haile.stackoverflowuser.di.activity.ActivityComponent
import com.haile.stackoverflowuser.di.bookmark.BookmarkComponent
import com.haile.stackoverflowuser.di.bookmark.BookmarkModule
import com.haile.stackoverflowuser.di.reputation.ReputationComponent
import com.haile.stackoverflowuser.di.reputation.ReputationModule
import com.haile.stackoverflowuser.di.userlist.UserListComponent
import com.haile.stackoverflowuser.di.userlist.UserListModule
import com.haile.stackoverflowuser.modules.dialog.LoadingFragment
import com.haile.stackoverflowuser.modules.main.MainContract
import com.haile.stackoverflowuser.modules.main.presenter.MainPresenter
import com.haile.stackoverflowuser.modules.reputation.view.ReputationFragment
import com.haile.stackoverflowuser.modules.userlist.view.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener {

    @Inject
    lateinit var presenter: MainPresenter

    private var activityComponent: ActivityComponent? = null
    private var userListComponent: UserListComponent? = null
    private var reputationComponent: ReputationComponent? = null
    private var bookmarkComponent: BookmarkComponent? = null

    private var loadingFragment: LoadingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //set fullscreen application
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        //inject activity component
        activityComponent = (application as? MyApplication)?.plusActivityComponent(this)
        activityComponent?.inject(this)

        presenter.onAttach(this)
        presenter.showUserListView()

        //header button listener
        ic_back?.setOnClickListener(this)
        ic_filter?.setOnClickListener (this)
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        (application as? MyApplication)?.clearActivityComponent()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ic_back -> {
                supportFragmentManager.popBackStack()
                showFilterOption()
            }
            R.id.ic_filter -> {
                val top = supportFragmentManager.findFragmentById(R.id.main_container)
                if(top is UserListFragment){
                    top.filterBookmark()
                }
            }
        }
    }


    /**
     * Manage User List Component
     */
    fun plusUserListComponent(fragment: UserListFragment): UserListComponent? {
        if (userListComponent == null) {
            userListComponent = activityComponent?.plusUserListComponent(UserListModule(fragment))
        }
        return userListComponent
    }
    fun clearUserListComponent() {
        userListComponent = null
    }

    /**
     * Manage Reputation Component
     */
    fun plusReputationComponent(fragment: ReputationFragment): ReputationComponent? {
        if (reputationComponent == null){
            reputationComponent = activityComponent?.plusReputationComponent(ReputationModule(fragment))
        }
        return reputationComponent
    }
    fun clearReputationComponent(){
        reputationComponent = null
    }

    /**
     * Manage Bookmark Component
     */
    fun plusBookmarkComponent() : BookmarkComponent? {
        if(bookmarkComponent == null){
            bookmarkComponent = activityComponent?.plusBookmarkComponent(BookmarkModule())
        }
        return bookmarkComponent
    }
    fun clearBookmarkComponent(){
        bookmarkComponent = null
    }

    /**
     * Show option to filter show bookmark only.
     * This function invoke when access User List View
     */
    override fun showFilterOption(){
        setHeaderTextView(getString(R.string.app_name))
        ic_back?.visibility = View.GONE
        ic_filter?.visibility = View.VISIBLE
    }

    /**
     * Show option back to User List View.
     */
    override fun showBackOption(){
        ic_back?.visibility = View.VISIBLE
        ic_filter?.visibility = View.GONE
    }

    /**
     * Show loading indicator when load data from api.
     */
    override fun showLoading() {
        if (loadingFragment == null) {
            loadingFragment = LoadingFragment()
            supportFragmentManager.beginTransaction()
                .add(loadingFragment as Fragment, LoadingFragment::class.java.simpleName).commit()
        }
    }

    /**
     * Dismiss Loading Dialog.
     */
    override fun dismissLoading() {
        loadingFragment?.dismissAllowingStateLoss()
        loadingFragment = null
    }

    fun setHeaderTextView(text : String?){
        tv_header?.text = text
    }
}
