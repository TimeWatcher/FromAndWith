package com.althurdinok.fromandwith.ui.screen.loginorsignup

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.althurdinok.fromandwith.ui.navigation.app.AppDestinationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginOrSignupViewModel @Inject constructor() : ViewModel() {
    fun navToSignup(navController: NavController) {
        navController.navigate(AppDestinationRoute.Signup.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
        }
    }

    fun navToLogin(navController: NavController) {
        navController.navigate(AppDestinationRoute.Login.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
        }
    }
}