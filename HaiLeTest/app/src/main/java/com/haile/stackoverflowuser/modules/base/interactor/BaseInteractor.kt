package com.haile.stackoverflowuser.modules.base.interactor

import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.entities.BaseErrorResponseData
import com.haile.stackoverflowuser.modules.base.BaseContract
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseInteractor {

    fun <T> onResponse(
        resultType: Response<T>,
        interactorOutput: BaseContract.BaseInteractorOutput<T>?
    ){
        if (resultType.isSuccessful) {
            val data = resultType.body()
            interactorOutput?.onSuccess(data)
        } else {
            interactorOutput?.onError(resultType.message())
        }
    }

    fun <T> onError(t: Throwable, interactorOutput: BaseContract.BaseInteractorOutput<T>?) {
        val errorCode: Int? = -1
        if (t is ConnectException || t is SocketTimeoutException ||
            t is UnknownHostException || t is IOException
        ) {
            interactorOutput?.onError("Unable to connect to server. Please try again later.")
        } else {
            interactorOutput?.onError(t.message)
        }
    }
}