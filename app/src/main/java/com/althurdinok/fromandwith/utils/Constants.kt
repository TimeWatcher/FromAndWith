package com.althurdinok.fromandwith.utils

object Constants {
    const val BASE_URL = "https://api2.bmob.cn/1/"
    const val APP_KEY = "9faeebb5fd282bd8bbfccfec0915c971"
    const val REST_API_KEY = "1fe80575c1dc839db58cf8811c08e831"

    object SharedPreferences {
        const val SP_NAME = "FromAndWith"
        const val IS_FIRST_TIME_LAUNCH = "FirstTimeLaunch"
        const val REMEMBER_USERNAME = "RememberUsername"
        const val REMEMBER_PASSWORD = "RememberPassword"
        const val HAS_LOGGED_IN = "LoggedIn"
        const val CURRENT_LOGGED_USERID = "CurrentLoggedUserId"
        const val CURRENT_SESSION_TOKEN = "CurrentSessionToken"
    }

    val SIGNUP_ERROR_CODES: Map<Int, String> = mapOf(
        202 to "用户名已存在",
        203 to "邮箱已存在",
        204 to "邮箱不能为空",
        301 to "邮箱格式错误"
    )

    val LOGIN_ERROR_CODES: Map<Int, String> = mapOf(
        101 to "用户名或密码错误",
        102 to "数据格式错误"
    )
}