package com.althurdinok.fromandwith.ui.screen.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.althurdinok.fromandwith.data.bean.ErrorData
import com.althurdinok.fromandwith.data.repository.auth.AuthRepository
import com.althurdinok.fromandwith.ui.common.ToastNotification
import com.althurdinok.fromandwith.ui.navigation.app.AppDestinationRoute
import com.althurdinok.fromandwith.ui.navigation.main.MainDestinationRoute
import com.althurdinok.fromandwith.utils.Constants
import com.althurdinok.fromandwith.utils.getSharedPreference
import com.althurdinok.fromandwith.utils.getSharedPreferenceEditor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = false,
    val autoLoginTried: Boolean = false,
    val currentSelectedRoute: String = MainDestinationRoute.Home.route
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())

    val mainUiState = _uiState.asStateFlow()

    fun onAutoLogin(context: Context, navController: NavController) {
        _uiState.update { it.copy(isLoading = true, autoLoginTried = true) }
        val sharedPreferences = getSharedPreference(context)
        // 通过登录或注册进入的，无需再次登录
        if (sharedPreferences.getBoolean(Constants.SharedPreferences.HAS_LOGGED_IN, false)) {
            _uiState.update { it.copy(isLoading = false) }
            return
        }
        val username =
            sharedPreferences.getString(Constants.SharedPreferences.REMEMBER_USERNAME, null)
        val password =
            sharedPreferences.getString(Constants.SharedPreferences.REMEMBER_PASSWORD, null)
        // 用户名密码为空，重新回到注册或登陆页面
        if (username == null || password == null) {
            _uiState.update { it.copy(isLoading = false) }
            navBackToLoginOrSignup(navController)
            return
        }
        viewModelScope.launch {
            authRepository.login(username, password).subscribe({ data ->
                getSharedPreferenceEditor(context)
                    .putBoolean(Constants.SharedPreferences.HAS_LOGGED_IN, true)
                    .putString(Constants.SharedPreferences.CURRENT_LOGGED_USERID, data.objectId)
                    .putString(Constants.SharedPreferences.CURRENT_SESSION_TOKEN, data.sessionToken)
                    .apply()
                _uiState.update { it.copy(isLoading = false) }
            }) { error ->
                // 400 请求出错，获取详细错误原因并报告
                when (error) {
                    is HttpException -> {
                        if (error.code() == 400) {
                            val errorData = Gson().fromJson(
                                error.response()?.errorBody()?.string() ?: "",
                                ErrorData::class.java
                            )
                            val msg =
                                if (Constants.LOGIN_ERROR_CODES.containsKey(errorData.code))
                                    Constants.LOGIN_ERROR_CODES[errorData.code]
                                else errorData.error
                            ToastNotification("登陆失败：$msg")
                        } else {
                            ToastNotification("登陆失效，请重新登录")
                        }
                        _uiState.update { it.copy(isLoading = false) }
                        navBackToLoginOrSignup(navController)
                    }
                    // 网络不通，重试
                    is UnknownHostException -> {
                        _uiState.update { it.copy(isLoading = true, autoLoginTried = false) }
                    }
                    // 其他情况直接报错后返回
                    else -> {
                        ToastNotification("登陆失效，请重新登录")
                        _uiState.update { it.copy(isLoading = false) }
                        navBackToLoginOrSignup(navController)
                    }
                }
            }
        }
    }

    fun onBottomNavigation(currentSelectedRoute: String) {
        // 记住当前选择的导航项，防止因为切换深色模式导致导航丢失
        _uiState.update { it.copy(currentSelectedRoute = currentSelectedRoute) }
    }

    private fun navBackToLoginOrSignup(navController: NavController) {
        navController.navigate(AppDestinationRoute.LoginOrSignup.route) {
            popUpTo(0)
            launchSingleTop = true
        }
    }
}