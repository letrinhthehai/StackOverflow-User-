package com.haile.stackoverflowuser.modules.base

interface BaseContract {
    interface BaseView {
    }
    interface BasePresenter {
        fun onAttach(view: BaseView?)
        fun onDestroy()
    }
    interface BaseInteractor {
        fun onDestroy()
    }
    interface BaseInteractorOutput<T> {
        fun onSuccess(data: T?)
        fun onError(error : String?)
    }
    interface BaseRouter
}