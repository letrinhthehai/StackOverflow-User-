package com.fossil.stackoverflowuser.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "UserDataLog")
class UserData {
    @ColumnInfo(name = "reputation")
    @SerializedName("reputation")
    var reputation: Int? = null

    @ColumnInfo(name = "lastAccessDate")
    @SerializedName("last_access_date")
    var lastAccessDate: Long? = null

    @PrimaryKey
    @ColumnInfo(name = "userId")
    @SerializedName("user_id")
    var userId: Int? = null

    @ColumnInfo(name = "location")
    @SerializedName("location")
    var location: String? = null

    @ColumnInfo(name = "profileImage")
    @SerializedName("profile_image")
    var profileImage: String? = null

    @ColumnInfo(name = "displayName")
    @SerializedName("display_name")
    var displayName: String? = null

    @ColumnInfo(name = "isBookmark")
    var isBookmark = false

}