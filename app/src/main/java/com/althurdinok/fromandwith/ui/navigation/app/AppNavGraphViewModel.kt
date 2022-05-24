package com.althurdinok.fromandwith.ui.navigation.app

import android.content.Context
import androidx.lifecycle.ViewModel
import com.althurdinok.fromandwith.utils.Constants
import com.althurdinok.fromandwith.utils.getSharedPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NavGraphUiState(
    val startAppDestinationRoute: AppDestinationRoute
)

class AppNavGraphViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavGraphUiState(AppDestinationRoute.OnBoarding))
    val uiState = _uiState.asStateFlow()

    fun selectDestinationRoute(context: Context) {
        val sharedPreferences = getSharedPreference(context = context)
        // 清除上一次的登录信息
        sharedPreferences.edit()
            .remove(Constants.SharedPreferences.CURRENT_LOGGED_USERID)
            .remove(Constants.SharedPreferences.CURRENT_SESSION_TOKEN)
            .remove(Constants.SharedPreferences.HAS_LOGGED_IN)
            .apply()
        // 第一次启动程序，进入引导页
        if (sharedPreferences.getBoolean(Constants.SharedPreferences.IS_FIRST_TIME_LAUNCH, true))
            _uiState.update { it.copy(startAppDestinationRoute = AppDestinationRoute.OnBoarding) }
        // 不是第一次启动程序，且存在登陆过的用户，直接进入主页自动登录
        else if (sharedPreferences.getString(
                Constants.SharedPreferences.REMEMBER_USERNAME,
                null
            ) != null
        )
            _uiState.update { it.copy(startAppDestinationRoute = AppDestinationRoute.Main) }
        // 不是第一次启动程序，但是没有保存登陆用户信息，进入登录或注册页面
        else
            _uiState.update { it.copy(startAppDestinationRoute = AppDestinationRoute.LoginOrSignup) }
    }
}