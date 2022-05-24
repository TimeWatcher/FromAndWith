package com.althurdinok.fromandwith.ui.screen.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.data.OnBoardingData
import com.althurdinok.fromandwith.data.repository.onborading.impl.FakeOnBoardingRepository
import com.althurdinok.fromandwith.ui.theme.LearnFromAndWithTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(navController: NavController, onBoardingViewModel: OnBoardingViewModel) {
    val context = LocalContext.current

    Surface(color = MaterialTheme.colorScheme.background) {
        val pagerState = rememberPagerState(initialPage = 0)
        var isLastPage by remember { mutableStateOf(false) }
        val density = LocalDensity.current
        Box(contentAlignment = Alignment.BottomCenter) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp)
            ) {
                HorizontalPager(
                    count = onBoardingViewModel.onBoardingData.value?.size ?: 0,
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) {
                    OnBoardingPagerContent(onBoardingViewModel.onBoardingData.value!![it])
                }
                isLastPage = pagerState.currentPage == pagerState.pageCount - 1
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            AnimatedVisibility(
                visible = isLastPage,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 100)
                ) + slideInVertically(
                    animationSpec = tween(durationMillis = 100),
                    initialOffsetY = { with(density) { (40.dp).roundToPx() } }
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 100)
                ) + slideOutVertically(
                    animationSpec = tween(durationMillis = 100),
                    targetOffsetY = { with(density) { (40.dp).roundToPx() } }
                ),
                modifier = Modifier
                    .padding(bottom = (LocalConfiguration.current.screenHeightDp / 6).dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        onBoardingViewModel.finishBoardingScreen(context, navController)
                    }
                ) {
                    Icon(Icons.Outlined.ArrowForward, contentDescription = null)
                }
            }
        }
    }

}

@Composable
fun OnBoardingPagerContent(model: OnBoardingData) {
    Column {
        Image(
            painter = if (model.imageRes is Int)
                painterResource(id = model.imageRes)
            else painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Image",
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 3 / 4).dp)
                .align(Alignment.CenterHorizontally)
                .absoluteOffset(y = -(LocalConfiguration.current.screenHeightDp / 9).dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = model.title,
            color = MaterialTheme.colorScheme.primary,
            style = androidx.compose.material.MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = model.description,
            color = MaterialTheme.colorScheme.secondary,
            style = androidx.compose.material.MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TestOnBoardScreen() {
    val viewModel = OnBoardingViewModel(FakeOnBoardingRepository())
    LearnFromAndWithTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 12.dp)
                        .align(Alignment.Center)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        OnBoardingPagerContent(viewModel.onBoardingData.value!![0])
                    }
                }
                FloatingActionButton(onClick = { }, modifier = Modifier.padding(80.dp)) {
                    Icon(Icons.Outlined.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}