package com.althurdinok.fromandwith.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.althurdinok.fromandwith.ui.app.FromAndWithViewModel
import com.althurdinok.fromandwith.ui.screen.main.home.HomeScreen
import com.althurdinok.fromandwith.ui.screen.main.home.HomeViewModel
import com.althurdinok.fromandwith.ui.screen.main.match.MatchScreen
import com.althurdinok.fromandwith.ui.screen.main.match.MatchViewModel
import com.althurdinok.fromandwith.ui.screen.main.message.MessageScreen
import com.althurdinok.fromandwith.ui.screen.main.message.MessageViewModel
import com.althurdinok.fromandwith.ui.screen.main.profile.ProfileScreen
import com.althurdinok.fromandwith.ui.screen.main.profile.ProfileViewModel

@Composable
fun MainNavGraph(mainNavController: NavHostController, fromAndWithViewModel: FromAndWithViewModel) {
    NavHost(
        navController = mainNavController,
        startDestination = MainDestinationRoute.Home.route
    ) {
        composable(MainDestinationRoute.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel = homeViewModel)
        }
        composable(MainDestinationRoute.Match.route) {
            val matchViewModel = hiltViewModel<MatchViewModel>()
            MatchScreen(matchViewModel = matchViewModel)
        }
        composable(MainDestinationRoute.Message.route) {
            val messageViewModel = hiltViewModel<MessageViewModel>()
            MessageScreen(messageViewModel = messageViewModel)
        }
        composable(MainDestinationRoute.Profile.route) {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(profileViewModel = profileViewModel, fromAndWithViewModel)
        }
    }
}