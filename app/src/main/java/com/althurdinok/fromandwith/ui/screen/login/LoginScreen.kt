package com.althurdinok.fromandwith.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.ui.common.GifImage
import com.althurdinok.fromandwith.ui.common.LoadingProgressSpinner
import com.althurdinok.fromandwith.ui.common.OutlinedValidationTextField
import com.althurdinok.fromandwith.ui.theme.DarkColorScheme
import com.althurdinok.fromandwith.ui.theme.LearnFromAndWithTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()

    var username by
    rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue()) }
    var password by
    rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue()) }

    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }
    val context = LocalContext.current

    if (uiState.snackMessage != null) {
        LaunchedEffect(key1 = uiState.snackMessage) {
            snackbarHostState.showSnackbar(
                uiState.snackMessage!!,
                if (uiState.snackActionLabel == null) "确认" else uiState.snackActionLabel,
                duration = SnackbarDuration.Indefinite
            )
            uiState.snackAction?.invoke()
            loginViewModel.clearSnackMessage()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    actionColor = DarkColorScheme.primary
                )
            }
        }) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    GifImage(
                        data = R.drawable.login,
                        modifier = Modifier.size(320.dp),
                        repeatCount = Int.MAX_VALUE
                    )
                    Text(
                        "继续踏上征程",
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 4.sp,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 32.dp)
                    ) {
                        Spacer(Modifier.height(24.dp))
                        OutlinedValidationTextField(
                            value = username,
                            onValueChange = {
                                username = it
                                loginViewModel.usernameValidation(username.text)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "用户名") },
                            singleLine = true,
                            errorMessage = uiState.usernameErrorMessage
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        OutlinedValidationTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                loginViewModel.passwordValidation(password.text)
                            },
                            label = { Text(text = "密码") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            errorMessage = uiState.passwordErrorMessage
                        )
                        Spacer(modifier = Modifier.padding(24.dp))
                        Button(
                            onClick = {
                                loginViewModel.usernameValidation(username.text)
                                loginViewModel.passwordValidation(password.text)
                                loginViewModel.login(
                                    context,
                                    navController,
                                    username.text,
                                    password.text
                                )
                            },
                            modifier = Modifier.size(240.dp, 48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "继续",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
        if (uiState.isLoading)
            LoadingProgressSpinner()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TestLoginScreen() {
    LearnFromAndWithTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { }) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        GifImage(
                            data = R.drawable.login,
                            modifier = Modifier.size(320.dp),
                            repeatCount = Int.MAX_VALUE
                        )
                        Text(
                            "继续踏上征程",
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 4.sp,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 32.dp)
                        ) {
                            Spacer(Modifier.height(24.dp))
                            OutlinedValidationTextField(
                                value = TextFieldValue(),
                                onValueChange = { },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = "用户名") },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            OutlinedValidationTextField(
                                value = TextFieldValue(),
                                onValueChange = { },
                                label = { Text(text = "密码") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(24.dp))
                            Button(
                                onClick = { },
                                modifier = Modifier.size(240.dp, 48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = "继续",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}