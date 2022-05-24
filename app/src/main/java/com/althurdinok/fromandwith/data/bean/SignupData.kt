package com.althurdinok.fromandwith.data.bean

data class SignupData(
    // 成功信息
    val createdAt: String,
    val objectId: String,
    val sessionToken: String,

    // 出错信息
    val code: String,
    val error: String
)