package com.haile.stackoverflowuser.modules.reputation

import com.haile.stackoverflowuser.entities.Reputation
import com.haile.stackoverflowuser.modules.base.BaseContract

interface ReputationContract {
    interface View: BaseContract.BaseView{
        fun onGetReputationListSuccess(items: ArrayList<Reputation?>?)
        fun onGetReputationListError(error : String?)

    }
    interface Presenter: BaseContract.BasePresenter{

    }

    interface Interactor: BaseContract.BaseInteractor{

    }
}