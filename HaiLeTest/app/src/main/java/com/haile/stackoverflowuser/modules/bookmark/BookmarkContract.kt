package com.haile.stackoverflowuser.modules.bookmark

import com.haile.stackoverflowuser.entities.UserData
import com.haile.stackoverflowuser.modules.base.BaseContract

interface BookmarkContract {
    interface View: BaseContract.BaseView{
        fun selectAllBookMark()
        fun addBookMark(userData: UserData)
        fun deleteBookMark(userData: UserData)
    }
}