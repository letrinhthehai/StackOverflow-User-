package com.haile.stackoverflowuser.modules.userlist

import com.haile.stackoverflowuser.entities.UserData
import com.haile.stackoverflowuser.modules.base.BaseContract

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