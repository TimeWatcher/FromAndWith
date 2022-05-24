package com.althurdinok.fromandwith.ui.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.althurdinok.fromandwith.ui.navigation.app.AppNavGraph
import com.althurdinok.fromandwith.ui.navigation.app.AppNavGraphViewModel
import com.althurdinok.fromandwith.ui.theme.DarkColorScheme
import com.althurdinok.fromandwith.ui.theme.LearnFromAndWithTheme
import com.althurdinok.fromandwith.ui.theme.LightColorScheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FromAndWithApp(fromAndWithViewModel: FromAndWithViewModel) {
    val uiState by fromAndWithViewModel.fromAndWithUiState.collectAsState()

    LearnFromAndWithTheme(darkTheme = uiState.darkTheme) {
        val navController = rememberNavController()
        val appNavGraphViewModel = hiltViewModel<AppNavGraphViewModel>()
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight
        SideEffect {
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setNavigationBarColor(
                if (uiState.darkTheme) DarkColorScheme.surface
                else LightColorScheme.surface,
                darkIcons = useDarkIcons
            )
            systemUiController.setStatusBarColor(
                if (uiState.darkTheme) DarkColorScheme.background
                else LightColorScheme.background
            )

        }
        AppNavGraph(navController = navController, appNavGraphViewModel, fromAndWithViewModel)
    }
}