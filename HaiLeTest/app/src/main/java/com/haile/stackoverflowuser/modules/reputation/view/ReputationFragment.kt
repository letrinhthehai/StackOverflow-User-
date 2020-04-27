package com.haile.stackoverflowuser.modules.reputation.view

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
import com.haile.stackoverflowuser.BuildConfig
import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.entities.Reputation
import com.haile.stackoverflowuser.modules.main.view.MainActivity
import com.haile.stackoverflowuser.modules.reputation.ReputationContract
import com.haile.stackoverflowuser.modules.reputation.adapter.ReputationAdapter
import com.haile.stackoverflowuser.modules.reputation.presenter.ReputationPresenter
import javax.inject.Inject

class ReputationFragment : Fragment(), ReputationContract.View {

    companion object{
        private const val ARG_USER_ID = BuildConfig.APPLICATION_ID + ".args.ARG_USER_ID"

        /**
         * Create new Instance of reputation fragment, the input value is the
         * Id of user to get reputation
         */
        fun newInstance(userId : Int) : ReputationFragment {
            val f = ReputationFragment()
            val args = Bundle()
            args.putInt(ARG_USER_ID, userId)
            f.arguments = args
            return f
        }
    }

    private var v: View? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: ReputationAdapter? = null
    private var swipeLayout : SwipeRefreshLayout? = null
    private var userId : Int? = null
    private var isRefresh = true
    private var isLoading = false


    @Inject
    lateinit var presenter: ReputationPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(v == null){
            v = inflater.inflate(R.layout.fragment_reputation, container, false)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).plusReputationComponent(this)?.inject(this)
        (activity as? MainActivity)?.showLoading()
        userId = arguments?.getInt(ARG_USER_ID)
        initView()
        presenter.onAttach(this)
        presenter.refreshUserData(userId)
    }

    /**
     * Init layout params and data for recycle view
     */
    private fun initView(){
        (activity as? MainActivity)?.showBackOption()
        recyclerView = v?.findViewById(R.id.rv_reputation)
        val layoutParams = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = ReputationAdapter(activity as Context)
        recyclerView?.layoutManager = layoutParams
        recyclerView?.adapter = adapter

        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView?.context,
                DividerItemDecoration.VERTICAL
            )
        )

        swipeLayout = v?.findViewById(R.id.sfr_reputation)
        swipeLayout?.setOnRefreshListener {
            isRefresh = true
            isLoading = false
            presenter.refreshUserData(userId)
        }
        initScrollListener()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        (activity as? MainActivity)?.clearReputationComponent()
        super.onDestroy()
    }

    /**
     * Function to check if the recycle view hit the bottom of list
     */
    private fun initScrollListener(){
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter?.reputations?.size?.minus(1)) {
                        //bottom of list
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    /**
     * Get more reputation, this function call when user scroll to the end of recycle view
     */
    private fun loadMore(){
        adapter?.reputations?.add(null)
        adapter?.notifyItemInserted(adapter?.reputations!!.size.minus(1))
        presenter.loadMore(userId)
    }

    /**
     * Handle reputation response data from server
     */
    override fun onGetReputationListSuccess(items: ArrayList<Reputation?>?) {
        if(isRefresh){
            //first time load data or reload by swipe down the Refresh Layout
            swipeLayout?.isRefreshing = false
            isRefresh = false
            adapter?.reputations = items
            adapter?.notifyDataSetChanged()
            (activity as? MainActivity)?.dismissLoading()
        } else if(isLoading){
            // Add more data to bottom of recycle view
            isLoading = false
            adapter?.reputations?.removeAt(adapter?.reputations!!.size - 1)
            val scrollPosition: Int = adapter?.reputations!!.size
            adapter?.notifyItemRemoved(scrollPosition)
            adapter?.reputations?.addAll(items!!)
            adapter?.notifyItemRangeChanged(scrollPosition, ReputationPresenter.TOTAL_REPUTATION_IN_PAGE)
        }
    }

    /**
     * Handle error response from server
     */
    override fun onGetReputationListError(error : String?) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
        if(isRefresh){
            swipeLayout?.isRefreshing = false
            isRefresh = false
        }
    }
}