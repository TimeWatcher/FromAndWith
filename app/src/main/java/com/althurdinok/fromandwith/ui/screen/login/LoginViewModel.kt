package com.althurdinok.fromandwith.ui.screen.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.althurdinok.fromandwith.data.bean.ErrorData
import com.althurdinok.fromandwith.data.bean.UserData
import com.althurdinok.fromandwith.data.repository.auth.AuthRepository
import com.althurdinok.fromandwith.ui.common.ToastNotification
import com.althurdinok.fromandwith.ui.navigation.app.AppDestinationRoute
import com.althurdinok.fromandwith.utils.Constants
import com.althurdinok.fromandwith.utils.getSharedPreferenceEditor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val usernameErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val snackMessage: String? = null,
    val snackActionLabel: String? = null,
    val snackAction: (() -> Unit)? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    private var validationSuccess = mutableMapOf(
        "username" to false,
        "password" to false
    )

    val uiState = _uiState.asStateFlow()

    fun login(context: Context, navController: NavController, username: String, password: String) {
        if (!checkValidationAllSuccess()) {
            showSnackMessage("信息有误，请正确填写信息后重试")
            return
        }
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepositoryImpl.login(username, password).subscribe({ userData ->
                _uiState.update { it.copy(isLoading = false) }
                onLoginSuccess(context, navController, userData, username, password)
            }, { throwable ->
                _uiState.update { it.copy(isLoading = false) }
                onLoginFailure(throwable)
            })
        }
    }

    private fun checkValidationAllSuccess(): Boolean {
        var success = true
        for ((_, v) in validationSuccess) {
            success = success and v
        }
        return success
    }

    private fun onLoginSuccess(
        context: Context,
        navController: NavController,
        userData: UserData,
        username: String,
        password: String
    ) {
        getSharedPreferenceEditor(context)
            .putBoolean(Constants.SharedPreferences.HAS_LOGGED_IN, true)
            .putString(Constants.SharedPreferences.CURRENT_SESSION_TOKEN, userData.sessionToken)
            .putString(Constants.SharedPreferences.CURRENT_LOGGED_USERID, userData.objectId)
            .putString(Constants.SharedPreferences.REMEMBER_USERNAME, username)
            .putString(Constants.SharedPreferences.REMEMBER_PASSWORD, password)
            .commit()
        ToastNotification(message = "欢迎回来，$username")
        navToMain(navController)
    }

    private fun onLoginFailure(throwable: Throwable) {
        if (throwable is HttpException) {
            if (throwable.code() == 400) {
                val errorData = Gson().fromJson(
                    throwable.response()?.errorBody()?.string() ?: "",
                    ErrorData::class.java
                )
                val msg =
                    if (Constants.LOGIN_ERROR_CODES.containsKey(errorData.code))
                        Constants.LOGIN_ERROR_CODES[errorData.code]
                    else errorData.error
                showSnackMessage("登陆失败：$msg")
            }
        } else {
            showSnackMessage("未知错误：${throwable.localizedMessage}")
        }
    }

    fun navToMain(navController: NavController) {
        navController.navigate(AppDestinationRoute.Main.route) {
            popUpTo(0)
            launchSingleTop = true
        }
    }

    fun usernameValidation(username: String) {
        if (username.isBlank()) {
            _uiState.update { it.copy(usernameErrorMessage = "用户名不能为空") }
            validationSuccess["username"] = false
        } else {
            _uiState.update { it.copy(usernameErrorMessage = null) }
            validationSuccess["username"] = true
        }
    }

    fun passwordValidation(password: String) {
        if (password.isBlank()) {
            _uiState.update { it.copy(passwordErrorMessage = "密码不能为空") }
            validationSuccess["password"] = false
        } else if (password.length < 6) {
            _uiState.update { it.copy(passwordErrorMessage = "密码不能小于6位") }
            validationSuccess["password"] = false
        } else {
            _uiState.update { it.copy(passwordErrorMessage = null) }
            validationSuccess["password"] = true
        }
    }

    private fun showSnackMessage(
        message: String,
        label: String? = null,
        action: (() -> Unit)? = null
    ) {
        _uiState.update {
            it.copy(
                snackMessage = message,
                snackActionLabel = label,
                snackAction = action
            )
        }
    }

    fun clearSnackMessage() {
        _uiState.update { it.copy(snackMessage = null, snackAction = null) }
    }
}