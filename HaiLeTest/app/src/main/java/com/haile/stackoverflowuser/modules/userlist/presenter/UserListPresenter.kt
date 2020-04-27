package com.haile.stackoverflowuser.modules.userlist.presenter

import com.haile.stackoverflowuser.entities.GetUserResponse
import com.haile.stackoverflowuser.modules.base.BaseContract
import com.haile.stackoverflowuser.modules.userlist.UserListContract
import com.haile.stackoverflowuser.modules.userlist.interactor.UserListInteractor
import com.haile.stackoverflowuser.modules.userlist.router.UserListRouter
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    var interactor : UserListInteractor?,
    var router: UserListRouter?
)  : UserListContract.Presenter {

    companion object{
        const val TOTAL_USER_IN_PAGE = 10
    }

    private var page = 1
    private var view: UserListContract.View? = null

    override fun onAttach(view: BaseContract.BaseView?) {
        this.view = view as? UserListContract.View?
        refreshUserData()
    }

    /**
     * Get data from index 1 to TOTAL_USER_IN_PAGE
     * This function call when the first time enter page or refresh by swipe layout
     */
    fun refreshUserData(){
        page = 1
        interactor?.getUserList(page, TOTAL_USER_IN_PAGE, getUserListListener)
    }

    /**
     * Get data from next page
     * This function call when recycle view hit bottom
     */
    fun loadMore(){
        page ++
        interactor?.getUserList(page, TOTAL_USER_IN_PAGE, getUserListListener)
    }

    private val getUserListListener = object : BaseContract.BaseInteractorOutput<GetUserResponse>{
        override fun onSuccess(data: GetUserResponse?) {
            if(data != null){
                view?.onGetUserListSuccess(data.items)
            }
        }

        override fun onError(error : String?) {
            view?.onGetUserListError(error)
        }
    }

    fun showReputationView(userId : Int){
        router?.showReputationView(userId)
    }
    fun showBookmarkView(){
        router?.showBookmarkView()
    }

    override fun onDestroy() {
        interactor?.onDestroy()
        interactor = null
        router = null
    }
}