package com.althurdinok.fromandwith.ui.navigation.app

sealed class AppDestinationRoute(val route: String) {
    object OnBoarding : AppDestinationRoute("OnBoarding")
    object LoginOrSignup : AppDestinationRoute("LoginOrSignup")
    object Signup : AppDestinationRoute("Signup")
    object Login : AppDestinationRoute("Login")
    object Main : AppDestinationRoute("Main")
}