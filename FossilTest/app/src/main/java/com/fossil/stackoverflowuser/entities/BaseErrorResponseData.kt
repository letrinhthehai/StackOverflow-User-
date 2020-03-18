package com.fossil.stackoverflowuser.entities

import com.google.gson.annotations.SerializedName

 open class BaseErrorResponseData {
    @SerializedName("error_id")
    var errorId: Int? = null
    @SerializedName("error_message")
    var errorMessage: String? = null
    @SerializedName("error_name")
    var errorName: String? = null
}