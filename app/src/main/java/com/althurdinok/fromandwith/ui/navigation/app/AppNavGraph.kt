package com.althurdinok.fromandwith.ui.navigation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.althurdinok.fromandwith.ui.app.FromAndWithViewModel
import com.althurdinok.fromandwith.ui.screen.login.LoginScreen
import com.althurdinok.fromandwith.ui.screen.login.LoginViewModel
import com.althurdinok.fromandwith.ui.screen.loginorsignup.LoginOrSignupScreen
import com.althurdinok.fromandwith.ui.screen.loginorsignup.LoginOrSignupViewModel
import com.althurdinok.fromandwith.ui.screen.main.MainScreen
import com.althurdinok.fromandwith.ui.screen.main.MainViewModel
import com.althurdinok.fromandwith.ui.screen.onboarding.OnBoardingScreen
import com.althurdinok.fromandwith.ui.screen.onboarding.OnBoardingViewModel
import com.althurdinok.fromandwith.ui.screen.signup.SignupScreen
import com.althurdinok.fromandwith.ui.screen.signup.SignupViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: AppNavGraphViewModel,
    fromAndWithViewModel: FromAndWithViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    viewModel.selectDestinationRoute(LocalContext.current)

    NavHost(
        navController = navController,
        startDestination = uiState.value.startAppDestinationRoute.route
    ) {
        composable(AppDestinationRoute.OnBoarding.route) {
            val onBoardingViewModel = hiltViewModel<OnBoardingViewModel>()
            OnBoardingScreen(
                navController = navController,
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(AppDestinationRoute.LoginOrSignup.route) {
            val loginOrSignupViewModel = hiltViewModel<LoginOrSignupViewModel>()
            LoginOrSignupScreen(navController = navController, loginOrSignupViewModel)
        }
        composable(AppDestinationRoute.Signup.route) {
            val signupViewModel = hiltViewModel<SignupViewModel>()
            SignupScreen(navController = navController, signupViewModel)
        }
        composable(AppDestinationRoute.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(AppDestinationRoute.Main.route) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                fromAndWithViewModel = fromAndWithViewModel
            )
        }
    }
}