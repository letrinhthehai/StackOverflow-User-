package com.fossil.stackoverflowuser.modules.main.presenter

import com.fossil.stackoverflowuser.modules.base.BaseContract
import com.fossil.stackoverflowuser.modules.main.MainContract
import com.fossil.stackoverflowuser.modules.main.router.MainRouter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private var router: MainRouter?
) : MainContract.Presenter {
    override fun onAttach(view: BaseContract.BaseView?) {
    }

    /**
     * Router to User List View
     */
    override fun showUserListView(){
        router?.showUserListView()
    }

    override fun onDestroy() {
        router = null
    }
}