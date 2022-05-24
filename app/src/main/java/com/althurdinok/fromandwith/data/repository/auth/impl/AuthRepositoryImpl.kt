package com.althurdinok.fromandwith.data.repository.auth.impl

import com.althurdinok.fromandwith.data.api.IAuthService
import com.althurdinok.fromandwith.data.bean.SessionValidity
import com.althurdinok.fromandwith.data.bean.SignupData
import com.althurdinok.fromandwith.data.bean.UserData
import com.althurdinok.fromandwith.data.repository.auth.AuthRepository
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class AuthRepositoryImpl(private val retrofit: Retrofit) : AuthRepository {

    override suspend fun signUp(
        username: String,
        password: String,
        email: String
    ): Observable<SignupData> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", username)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("email", email)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return retrofit.create(IAuthService::class.java)
            .signup(requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun login(username: String, password: String): Observable<UserData> {
        return retrofit.create(IAuthService::class.java)
            .login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun checkSession(sessionToken: String): Observable<SessionValidity> {
        return retrofit.create(IAuthService::class.java)
            .checkSession(sessionToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}