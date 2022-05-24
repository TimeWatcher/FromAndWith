package com.althurdinok.fromandwith.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.md5(): String {
    try {
        val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
        val digest: ByteArray = instance.digest(this.toByteArray())//对字符串加密，返回字节数组
        val buffer = StringBuffer()
        for (b in digest) {
            val i: Int = b.toInt() and 0xff//获取低八位有效值
            var hexString = Integer.toHexString(i)//将整数转化为16进制
            if (hexString.length < 2) {
                hexString = "0$hexString"//如果是一位的话，补0
            }
            buffer.append(hexString)
        }
        return buffer.toString()

    } catch (e: NoSuchAlgorithmException) {
        Log.e("MD5", e.localizedMessage ?: "")
    }
    return ""
}

fun getSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        Constants.SharedPreferences.SP_NAME,
        Context.MODE_PRIVATE
    )
}

fun getSharedPreferenceEditor(context: Context): SharedPreferences.Editor {
    return context.getSharedPreferences(
        Constants.SharedPreferences.SP_NAME,
        Context.MODE_PRIVATE
    ).edit()
}