package com.fossil.stackoverflowuser.modules.main

import com.fossil.stackoverflowuser.modules.base.BaseContract

interface MainContract {
    interface View : BaseContract.BaseView {
        fun showFilterOption()
        fun showBackOption()
        fun showLoading()
        fun dismissLoading()
    }
    interface Presenter : BaseContract.BasePresenter {
        fun showUserListView()

    }
    interface Router : BaseContract.BaseRouter {
        fun showUserListView()
    }
}