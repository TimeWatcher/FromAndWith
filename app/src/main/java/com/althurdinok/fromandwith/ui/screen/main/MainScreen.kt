package com.althurdinok.fromandwith.ui.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.althurdinok.fromandwith.ui.app.FromAndWithViewModel
import com.althurdinok.fromandwith.ui.common.LoadingProgressSpinner
import com.althurdinok.fromandwith.ui.navigation.main.MainDestinationRoute
import com.althurdinok.fromandwith.ui.navigation.main.MainNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    fromAndWithViewModel: FromAndWithViewModel
) {
    val uiState by mainViewModel.mainUiState.collectAsState()
    val context = LocalContext.current

    if (!uiState.autoLoginTried)
        mainViewModel.onAutoLogin(context, navController)

    var currentSelectedRoute by remember { mutableStateOf(uiState.currentSelectedRoute) }
    val mainNavController = rememberNavController()
    val activity = LocalContext.current as? Activity

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentSelectedRoute) {
                currentSelectedRoute = it.route
                mainViewModel.onBottomNavigation(currentSelectedRoute)
                mainNavController.navigate(currentSelectedRoute) {
                    popUpTo(mainNavController.graph.findStartDestination().id)/* {
                        saveState = false
                    }*/
                    launchSingleTop = true
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            MainNavGraph(
                mainNavController = mainNavController,
                fromAndWithViewModel = fromAndWithViewModel
            )
        }
        BackHandler {
            if (currentSelectedRoute != MainDestinationRoute.Home.route) {
                currentSelectedRoute = mainNavController.graph.findStartDestination().route ?: ""
                mainViewModel.onBottomNavigation(currentSelectedRoute)
                mainNavController.navigate(currentSelectedRoute) {
                    popUpTo(mainNavController.graph.findStartDestination().id) {
                        saveState = false
                    }
                    launchSingleTop = true
                }
            } else {
                activity?.finish()
            }
        }
    }

    if (uiState.isLoading)
        LoadingProgressSpinner()
}


@Composable
fun BottomNavigationBar(
    currentSelectedRoute: String,
    onItemSelected: (MainDestinationRoute) -> Unit
) {
    val items = MainDestinationRoute.Items.list
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach {
            BottomNavItem(
                selected = it.route == currentSelectedRoute,
                onClick = { onItemSelected(it) },
                item = it
            )
        }
    }
}

@Composable
fun BottomNavItem(
    selected: Boolean,
    onClick: () -> Unit,
    item: MainDestinationRoute,
) {
    val background =
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (item.icon) {
                is ImageVector -> Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = contentColor
                )
                is Int -> Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    tint = contentColor
                )
            }
            AnimatedVisibility(visible = selected) {
                Text(text = item.title, color = contentColor)
            }
        }
    }
}