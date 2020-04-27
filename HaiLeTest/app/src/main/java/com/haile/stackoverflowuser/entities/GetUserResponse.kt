package com.haile.stackoverflowuser.entities

import com.google.gson.annotations.SerializedName

class GetUserResponse : BaseErrorResponseData(){
    @SerializedName("items")
    var items: ArrayList<UserData?>? = null

    @SerializedName("has_more")
    var hasMore: Boolean? = null

    @SerializedName("quota_max")
    var quotaMax: Int? = null

    @SerializedName("quota_remaining")
    var quotaRemaining: Int? = null
}