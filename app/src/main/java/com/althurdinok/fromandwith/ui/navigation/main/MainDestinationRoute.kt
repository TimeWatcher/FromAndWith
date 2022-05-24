package com.althurdinok.fromandwith.ui.navigation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import com.althurdinok.fromandwith.R

sealed class MainDestinationRoute(val route: String, val title: String, val icon: Any) {
    object Home : MainDestinationRoute("Home", "广场", Icons.Filled.Home)
    object Match : MainDestinationRoute("Match", "匹配", R.drawable.ic_baseline_people_24)
    object Message : MainDestinationRoute("Message", "聊天", R.drawable.ic_baseline_message_24)
    object Profile : MainDestinationRoute("Profile", "我的", Icons.Filled.Person)

    object Items {
        val list = listOf(
            Home,
            Match,
            Message,
            Profile
        )
    }
}
