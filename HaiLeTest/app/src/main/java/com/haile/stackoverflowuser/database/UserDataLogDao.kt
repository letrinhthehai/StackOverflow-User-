package com.haile.stackoverflowuser.database

import androidx.room.*
import com.haile.stackoverflowuser.entities.UserData

@Dao
interface UserDataLogDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(item : UserData)

    @Query("DELETE FROM UserDataLog WHERE userId LIKE :userId")
    fun deleteItem(userId : Int)

    @Query("SELECT * FROM UserDataLog")
    fun selectAll() : List<UserData>
}