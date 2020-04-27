package com.haile.stackoverflowuser.services

import com.haile.stackoverflowuser.entities.GetReputationResponse
import com.haile.stackoverflowuser.entities.GetUserResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface StackExchangeServices {
    @GET("users")
    fun getCardList(@Query("page") page: Int, @Query("pagesize") pageSize: Int, @Query("site") site: String): Observable<Response<GetUserResponse>>

    @GET("users/{userId}/reputation-history")
    fun getReputation(
        @Path("userId") userId: Int, @Query("page") page: Int, @Query("pagesize") pageSize: Int, @Query("site") site: String
    ): Observable<Response<GetReputationResponse>>
}