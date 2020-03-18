package com.fossil.stackoverflowuser.modules.bookmark.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fossil.stackoverflowuser.R
import com.fossil.stackoverflowuser.database.AppDatabase
import com.fossil.stackoverflowuser.database.HandlerWorkerThread
import com.fossil.stackoverflowuser.entities.UserData
import com.fossil.stackoverflowuser.modules.bookmark.BookmarkContract
import com.fossil.stackoverflowuser.modules.main.view.MainActivity
import com.fossil.stackoverflowuser.modules.userlist.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_bookmark.*
import javax.inject.Inject

class BookmarkFragment : Fragment(), BookmarkContract.View, UserAdapter.UserClickListener {

    private var v: View? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: UserAdapter? = null

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
            v = inflater.inflate(R.layout.fragment_bookmark, container, false)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.plusBookmarkComponent()?.inject(this)
        (activity as? MainActivity)?.setHeaderTextView(getString(R.string.bookmark))
        initView()
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.clearBookmarkComponent()
        super.onDestroy()
    }

    /**
     * Init Layout Params and Data to Recycle view
     */
    private fun initView(){
        (activity as? MainActivity)?.showBackOption()
        recyclerView = v?.findViewById(R.id.rv_bookmark)
        adapter = UserAdapter(activity as Context)
        adapter?.listener = this
        recyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView?.context,
                DividerItemDecoration.VERTICAL
            )
        )
        selectAllBookMark()
    }

    /**
     * Get all bookmarks data from local database
     */
     override fun selectAllBookMark(){
        val task = Runnable {
            val bookmarks = appDatabase?.userDataLogDao()?.selectAll()
            activity?.runOnUiThread {
                if(bookmarks != null && bookmarks.isNotEmpty()){
                    tv_no_bookmark?.visibility = View.GONE
                    rv_bookmark?.visibility = View.VISIBLE
                    adapter?.users = bookmarks.toMutableList()
                    adapter?.notifyDataSetChanged()
                } else {
                    tv_no_bookmark?.visibility = View.VISIBLE
                    rv_bookmark?.visibility = View.GONE
                }
            }
        }
        workerThread.postTask(task)
    }

    override fun onClickUser(userId: Int, userName: String?) {
    }


    override fun onClickBookmark(isBookmark: Boolean, user: UserData) {
        if(isBookmark) addBookMark(user) else deleteBookMark (user)
    }

    /**
     * Add new user to bookmark list
     */
    override fun addBookMark(userData: UserData){
        val task = Runnable {
            appDatabase?.userDataLogDao()?.insert(userData)
        }
        workerThread.postTask(task)
    }

    /**
     * Delete selected bookmark
     */
    override fun deleteBookMark(userData: UserData){
        val task = Runnable {
            appDatabase?.userDataLogDao()?.deleteItem(userData.userId!!)
        }
        workerThread.postTask(task)
    }
}