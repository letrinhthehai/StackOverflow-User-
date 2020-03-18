package com.fossil.stackoverflowuser.entities

import com.google.gson.annotations.SerializedName

class Reputation {
    @SerializedName("reputation_history_type")
    var reputationHistoryType: String? = null

    @SerializedName("reputation_change")
    var reputationChange: Int? = null

    @SerializedName("post_id")
    var postId: Int? = null

    @SerializedName("creation_date")
    var creationDate: Long? = null
}