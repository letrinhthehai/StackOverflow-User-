package com.haile.stackoverflowuser.modules.main

import com.haile.stackoverflowuser.modules.base.BaseContract

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