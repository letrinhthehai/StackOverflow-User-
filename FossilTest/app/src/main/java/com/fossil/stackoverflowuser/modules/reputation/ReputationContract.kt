package com.fossil.stackoverflowuser.modules.reputation

import com.fossil.stackoverflowuser.entities.Reputation
import com.fossil.stackoverflowuser.modules.base.BaseContract

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