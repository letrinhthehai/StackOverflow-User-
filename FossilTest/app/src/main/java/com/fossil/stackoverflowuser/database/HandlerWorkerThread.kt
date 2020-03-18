package com.fossil.stackoverflowuser.database

import android.os.Handler
import android.os.HandlerThread

class HandlerWorkerThread (threadName: String) : HandlerThread(threadName){

    private lateinit var workerHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()

        workerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        if (::workerHandler.isInitialized){
            workerHandler.post(task)
        }
    }
}