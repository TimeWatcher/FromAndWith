package com.althurdinok.fromandwith.data.api

import com.althurdinok.fromandwith.data.bean.SessionValidity
import com.althurdinok.fromandwith.data.bean.SignupData
import com.althurdinok.fromandwith.data.bean.UserData
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface IAuthService {

    @POST("users")
    fun signup(
        @Body signupInfo: RequestBody
    ): Observable<SignupData>

    @GET("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ) : Observable<UserData>

    @GET("checkSession/{sessionToken}")
    fun checkSession(
        @Path("sessionToken") sessionToken: String
    ): Observable<SessionValidity>
}