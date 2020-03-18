package com.fossil.stackoverflowuser.modules.reputation.presenter

import com.fossil.stackoverflowuser.entities.GetReputationResponse
import com.fossil.stackoverflowuser.modules.base.BaseContract
import com.fossil.stackoverflowuser.modules.reputation.ReputationContract
import com.fossil.stackoverflowuser.modules.reputation.interactor.ReputationInteractor
import javax.inject.Inject

class ReputationPresenter @Inject constructor(
    var interactor: ReputationInteractor?
)  : ReputationContract.Presenter{

    companion object{
        const val TOTAL_REPUTATION_IN_PAGE = 20
    }
    private var page = 1
    private var view : ReputationContract.View? = null

    override fun onAttach(view: BaseContract.BaseView?) {
        this.view = view as ReputationContract.View?
    }

    /**
     *  Get new reputation list.
     *  This function invoke when first time load page or swipe down to refresh list.
     */
    fun refreshUserData(userId: Int?){
        if(userId != null){
            page = 1
            interactor?.getReputationList(userId, page, TOTAL_REPUTATION_IN_PAGE, getReputationListener)
        }
    }

    /**
     *  Load more Reputations.
     *  This function invoke when user scroll down reputation list.
     */
    fun loadMore(userId: Int?){
        if(userId != null){
            page = 1
            interactor?.getReputationList(userId, page, TOTAL_REPUTATION_IN_PAGE, getReputationListener)
        }

    }

    /**
     *  Listen reputation data response from interactor and invoke to reputation fragment
     */
    private val getReputationListener = object : BaseContract.BaseInteractorOutput<GetReputationResponse>{
        override fun onSuccess(data: GetReputationResponse?) {
            if(data != null){
                view?.onGetReputationListSuccess(data.items)
            }
        }
        override fun onError(error : String?) {
            view?.onGetReputationListError(error)
        }

    }

    override fun onDestroy() {
        interactor?.onDestroy()
        interactor = null
    }
}