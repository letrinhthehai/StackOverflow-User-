package com.fossil.stackoverflowuser.modules.userlist

import com.fossil.stackoverflowuser.entities.UserData
import com.fossil.stackoverflowuser.modules.base.BaseContract

interface UserListContract {
    interface View: BaseContract.BaseView{
        fun onGetUserListSuccess(items: ArrayList<UserData?>?)
        fun onGetUserListError(error : String?)

    }
    interface Presenter: BaseContract.BasePresenter{

    }
    interface Router: BaseContract.BaseRouter{

    }
    interface Interactor: BaseContract.BaseInteractor{

    }
}