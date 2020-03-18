package com.fossil.stackoverflowuser.modules.userlist.interactor

import com.fossil.stackoverflowuser.entities.GetUserResponse
import com.fossil.stackoverflowuser.modules.base.BaseContract
import com.fossil.stackoverflowuser.modules.base.interactor.BaseInteractor
import com.fossil.stackoverflowuser.modules.userlist.UserListContract
import com.fossil.stackoverflowuser.services.Config.PARAM_API_SITE
import com.fossil.stackoverflowuser.services.StackExchangeServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListInteractor @Inject constructor(
    val services: StackExchangeServices,
    private val compositeDisposable: CompositeDisposable
) : BaseInteractor(), UserListContract.Interactor {

    fun getUserList(page : Int, pageSize : Int, listener : BaseContract.BaseInteractorOutput<GetUserResponse>){
        val disposable = services.getCardList(page, pageSize, PARAM_API_SITE)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onResponse(it, listener)
                }, {
                   onError(it, listener)
                }
            )
        compositeDisposable.add(disposable)

    }
    override fun onDestroy() {
        compositeDisposable.clear()
    }
}