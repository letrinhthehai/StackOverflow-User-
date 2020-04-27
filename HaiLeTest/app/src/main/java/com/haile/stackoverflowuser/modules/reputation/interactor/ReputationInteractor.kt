package com.haile.stackoverflowuser.modules.reputation.interactor

import com.haile.stackoverflowuser.entities.GetReputationResponse
import com.haile.stackoverflowuser.modules.base.BaseContract
import com.haile.stackoverflowuser.modules.base.interactor.BaseInteractor
import com.haile.stackoverflowuser.modules.reputation.ReputationContract
import com.haile.stackoverflowuser.services.Config.PARAM_API_SITE
import com.haile.stackoverflowuser.services.StackExchangeServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReputationInteractor @Inject constructor(
    val services: StackExchangeServices,
    private val compositeDisposable: CompositeDisposable
) : BaseInteractor(), ReputationContract.Interactor {

    fun getReputationList(userId : Int, page: Int, pageSize : Int, listener: BaseContract.BaseInteractorOutput<GetReputationResponse>){
        val disposable = services.getReputation(userId, page, pageSize, PARAM_API_SITE)
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