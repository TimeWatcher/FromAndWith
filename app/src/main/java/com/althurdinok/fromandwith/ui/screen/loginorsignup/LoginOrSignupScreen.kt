package com.althurdinok.fromandwith.ui.screen.loginorsignup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.ui.common.GifImage
import com.althurdinok.fromandwith.ui.theme.LearnFromAndWithTheme


@Composable
fun LoginOrSignupScreen(navController: NavController, viewModel: LoginOrSignupViewModel) {
    Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.46f)
                        .padding(
                            top = 4.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 4.dp
                        )
                ) {
                    GifImage(R.drawable.login_or_signup, modifier = Modifier.fillMaxSize())
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Text(
                            "欢迎来到三人行",
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 4.sp,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            "结交行者，共同学习。不论是知识交换或是多路学习，你都能找到同行的人。",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(
                                top = 28.dp,
                                start = 36.dp,
                                end = 36.dp,
                                bottom = 64.dp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.navToSignup(navController) },
                            modifier = Modifier
                                .width(280.dp)
                                .height(42.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = "成为行者")
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedButton(
                            onClick = { viewModel.navToLogin(navController) },
                            modifier = Modifier
                                .width(280.dp)
                                .height(42.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = "直接登录")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TestLOS() {
    LearnFromAndWithTheme {
        Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.46f)
                            .padding(
                                top = 4.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 4.dp
                            )
                    ) {
                        GifImage(R.drawable.login_or_signup, modifier = Modifier.fillMaxSize())
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Text(
                                "欢迎来到三人行",
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 4.sp,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                "结交学友，共同学习。不论是知识交换或是多路学习，你都能找到同行的人。",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(
                                    top = 28.dp,
                                    start = 36.dp,
                                    end = 36.dp,
                                    bottom = 64.dp
                                ),
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(42.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = "成为行者")
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(42.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = "直接登录")
                            }
                        }
                    }
                }
            }
        }
    }
}