package com.haile.stackoverflowuser.entities

import com.google.gson.annotations.SerializedName

class GetReputationResponse : BaseErrorResponseData() {
    @SerializedName("items")
    var items: ArrayList<Reputation?>? = null

    @SerializedName("has_more")
    var hasMore: Boolean? = null

    @SerializedName("quota_max")
    var quotaMax: Int? = null

    @SerializedName("quota_remaining")
    var quotaRemaining: Int? = null
}