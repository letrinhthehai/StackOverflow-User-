package com.fossil.stackoverflowuser.modules.userlist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fossil.stackoverflowuser.R
import com.fossil.stackoverflowuser.database.AppDatabase
import com.fossil.stackoverflowuser.database.HandlerWorkerThread
import com.fossil.stackoverflowuser.entities.UserData
import com.fossil.stackoverflowuser.modules.main.view.MainActivity
import com.fossil.stackoverflowuser.modules.userlist.UserListContract
import com.fossil.stackoverflowuser.modules.userlist.adapter.UserAdapter
import com.fossil.stackoverflowuser.modules.userlist.presenter.UserListPresenter
import com.fossil.stackoverflowuser.modules.userlist.presenter.UserListPresenter.Companion.TOTAL_USER_IN_PAGE
import javax.inject.Inject


class UserListFragment : Fragment(), UserListContract.View, UserAdapter.UserClickListener {

    private var v: View? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: UserAdapter? = null
    private var swipeLayout: SwipeRefreshLayout? = null
    private var isRefresh = true
    private var isLoading = false
    private var bookmarks: MutableList<UserData>? = null

    @Inject
    lateinit var presenter: UserListPresenter

    @Inject
    lateinit var workerThread: HandlerWorkerThread

    @set:Inject
    var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_user_list, container, false)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.plusUserListComponent(this)?.inject(this)
        initView()
        if (!workerThread.isAlive) {
            workerThread.start()
        }
        presenter.onAttach(this)
    }

    /**
     * Get new data to recycle view
     * This function call when first time enter app or after back from bookmark screen
     */
    override fun onResume() {
        super.onResume()
        isRefresh = true
        (activity as? MainActivity)?.showLoading()
        presenter.refreshUserData()
    }

    private fun initView() {
        (activity as? MainActivity)?.showFilterOption()
        recyclerView = v?.findViewById(R.id.rv_user)
        //Init UI for recycle view
        val layoutParams = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = UserAdapter(activity as Context)
        adapter?.listener = this
        recyclerView?.layoutManager = layoutParams
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView?.context,
                DividerItemDecoration.VERTICAL
            )
        )
        //Init UI for Swipe Layout
        swipeLayout = v?.findViewById(R.id.swipe_refresh)
        swipeLayout?.setOnRefreshListener {
            isRefresh = true
            isLoading = false
            presenter.refreshUserData()
        }

        initScrollListener()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        workerThread.looper?.quit()
        workerThread.quit()
        (activity as? MainActivity)?.clearUserListComponent()
        super.onDestroy()

    }

    override fun onGetUserListSuccess(items: ArrayList<UserData?>?) {
        if (items != null) {
            selectAllBookMark(items)
        }
    }

    private fun addUsersToRecycle(items: ArrayList<UserData?>) {
        checkBookmark(items)
        activity?.runOnUiThread {
            if (isRefresh) {
                swipeLayout?.isRefreshing = false
                isRefresh = false
                adapter?.users = items
                adapter?.notifyDataSetChanged()
                (activity as? MainActivity)?.dismissLoading()
            } else if (isLoading) {
                isLoading = false
                adapter?.users?.removeAt(adapter!!.users!!.size - 1)
                val scrollPosition: Int = adapter!!.users!!.size
                adapter?.notifyItemRemoved(scrollPosition)
                adapter?.users?.addAll(items)
                adapter?.notifyItemRangeChanged(scrollPosition, TOTAL_USER_IN_PAGE)
            }
        }
    }

    /**
     * Turn on Bookmark flag for the item has been book mark before
     * Update data of this item
     */
    private fun checkBookmark(users: ArrayList<UserData?>) {
        bookmarks?.forEach { bookmark ->
            users.forEach {
                if (it?.userId == bookmark.userId) {
                    it?.isBookmark = true
                    addBookMark(it!!)
                }
            }
        }
    }

    override fun onGetUserListError(error : String?) {
        if (isRefresh) {
            swipeLayout?.isRefreshing = false
            isRefresh = false
        }
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    /**
     * Listen recycle view hit bottom to load more item.
     */
    private fun initScrollListener() {
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter?.users?.size?.minus(1)
                    ) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        adapter?.users?.add(null)
        adapter?.notifyItemInserted(adapter?.users!!.size.minus(1))
        presenter.loadMore()
    }

    override fun onClickUser(userId: Int, userName: String?) {
        presenter.showReputationView(userId)
        (activity as? MainActivity)?.setHeaderTextView(userName)
    }

    override fun onClickBookmark(isBookmark: Boolean, user: UserData) {
        if (isBookmark) addBookMark(user) else deleteBookMark(user)
    }

    /**
     * Get all bookmark item from data base
     */
    private fun selectAllBookMark(items: ArrayList<UserData?>) {
        val task = Runnable {
            bookmarks = appDatabase?.userDataLogDao()?.selectAll()?.toMutableList()
            addUsersToRecycle(items)
        }
        workerThread.postTask(task)
    }

    /**
     * Add item to DB bookmark list
     */
    private fun addBookMark(userData: UserData) {
        val task = Runnable {
            appDatabase?.userDataLogDao()?.insert(userData)
        }
        workerThread.postTask(task)
    }

    /**
     * Remove item from db bookmark list
     */
    private fun deleteBookMark(userData: UserData) {
        val task = Runnable {
            appDatabase?.userDataLogDao()?.deleteItem(userData.userId!!)
        }
        workerThread.postTask(task)
    }

    fun filterBookmark() {
        presenter.showBookmarkView()
    }
}