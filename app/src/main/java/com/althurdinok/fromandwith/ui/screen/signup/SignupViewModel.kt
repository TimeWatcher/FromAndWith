package com.althurdinok.fromandwith.ui.screen.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.althurdinok.fromandwith.data.bean.SignupData
import com.althurdinok.fromandwith.data.repository.auth.AuthRepository
import com.althurdinok.fromandwith.ui.common.ToastNotification
import com.althurdinok.fromandwith.ui.navigation.app.AppDestinationRoute
import com.althurdinok.fromandwith.utils.Constants
import com.althurdinok.fromandwith.utils.getSharedPreferenceEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

data class SignupUiState(
    val isLoading: Boolean = false,
    val usernameErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val snackMessage: String? = null,
    val snackActionLabel: String? = null,
    val snackAction: (() -> Unit)? = null
)

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()
    private var validationSuccess = mutableMapOf<String, Boolean>(
        "username" to false,
        "password" to false,
        "email" to false
    )

    fun signup(
        context: Context,
        navController: NavController,
        username: String,
        password: String,
        email: String
    ) {
        if (!checkValidationAllSuccess()) {
            showSnackMessage("信息有误，请正确填写信息后重试")
            return
        }
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepositoryImpl.signUp(username, password, email)
                .subscribe({
                    _uiState.update { it.copy(isLoading = false) }
                    if (it.code == null) {
                        onSignupSuccess(
                            context = context,
                            navController,
                            username = username,
                            password = password,
                            signupData = it
                        )
                    } else {
                        onSignupFailure(it.code.toInt(), it.error)
                    }
                }) { throwable ->
                    _uiState.update { it.copy(isLoading = false) }
                    when (throwable) {
                        is HttpException -> onNetworkError(throwable.code())
                        is UnknownHostException -> {
                            showSnackMessage(message = "没有网络连接，请检查后重试", label = "重试") {
                                signup(context, navController, username, password, email)
                            }
                        }
                        else -> {
                            throwable.printStackTrace()
                            showSnackMessage("未知错误：${throwable.localizedMessage}")
                        }
                    }
                }
        }
    }

    private fun checkValidationAllSuccess(): Boolean {
        var success = true
        for ((_, v) in validationSuccess) {
            success = success and v
        }
        return success
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

    fun emailValidation(email: String) {
        if (!email.matches(Regex("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"))) {
            _uiState.update { it.copy(emailErrorMessage = "邮箱格式错误") }
            validationSuccess["email"] = false
        } else {
            _uiState.update { it.copy(emailErrorMessage = null) }
            validationSuccess["email"] = true
        }
    }

    private fun onNetworkError(errorCode: Int) {
        showSnackMessage("HTTP网络错误：$errorCode")
    }

    private fun onSignupSuccess(
        context: Context,
        navController: NavController,
        username: String,
        password: String,
        signupData: SignupData?
    ) {
        if (signupData != null) {
            getSharedPreferenceEditor(context = context)
                .putBoolean(Constants.SharedPreferences.HAS_LOGGED_IN, true)
                .putString(
                    Constants.SharedPreferences.CURRENT_SESSION_TOKEN,
                    signupData.sessionToken
                )
                .putString(Constants.SharedPreferences.CURRENT_LOGGED_USERID, signupData.objectId)
                .putString(Constants.SharedPreferences.REMEMBER_USERNAME, username)
                .putString(Constants.SharedPreferences.REMEMBER_PASSWORD, password)
                .commit()
            ToastNotification(message = "注册成功，欢迎您，$username")
            navToMain(navController)
        }
    }

    private fun onSignupFailure(errorCode: Int, message: String? = null) {
        val failureMessage =
            if (message == null) "注册失败： $message ($errorCode)"
            else "注册失败： ${Constants.SIGNUP_ERROR_CODES[errorCode]} ($errorCode)"
        showSnackMessage(failureMessage)
    }

    private fun navToMain(navController: NavController) {
        navController.navigate(AppDestinationRoute.Main.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
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