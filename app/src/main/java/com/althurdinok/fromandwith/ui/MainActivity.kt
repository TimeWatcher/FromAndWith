package com.althurdinok.fromandwith.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.althurdinok.fromandwith.ui.app.FromAndWithApp
import com.althurdinok.fromandwith.ui.app.FromAndWithViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fromAndWithViewModel = hiltViewModel<FromAndWithViewModel>()
            fromAndWithViewModel.setThemeMode(isSystemInDarkTheme())
            FromAndWithApp(fromAndWithViewModel)
        }
    }
}
