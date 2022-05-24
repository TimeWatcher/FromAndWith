package com.althurdinok.fromandwith.data.repository.auth

import com.althurdinok.fromandwith.data.bean.SessionValidity
import com.althurdinok.fromandwith.data.bean.SignupData
import com.althurdinok.fromandwith.data.bean.UserData
import io.reactivex.Observable
import retrofit2.Response

interface AuthRepository {
    suspend fun signUp(
        username: String,
        password: String,
        email: String
    ): Observable<SignupData>

    suspend fun login(username: String, password: String): Observable<UserData>

    suspend fun checkSession(sessionToken: String): Observable<SessionValidity>
}