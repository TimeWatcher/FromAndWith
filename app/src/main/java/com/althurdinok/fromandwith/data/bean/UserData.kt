package com.althurdinok.fromandwith.data.bean

data class UserData(
    val createdAt: String,
    val email: String,
    val emailVerified: Boolean,
    val objectId: String,
    val sessionToken: String,
    val updatedAt: String,
    val username: String
)