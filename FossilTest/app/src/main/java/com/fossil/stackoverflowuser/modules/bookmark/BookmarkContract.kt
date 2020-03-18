package com.fossil.stackoverflowuser.modules.bookmark

import com.fossil.stackoverflowuser.entities.UserData
import com.fossil.stackoverflowuser.modules.base.BaseContract

interface BookmarkContract {
    interface View: BaseContract.BaseView{
        fun selectAllBookMark()
        fun addBookMark(userData: UserData)
        fun deleteBookMark(userData: UserData)
    }
}