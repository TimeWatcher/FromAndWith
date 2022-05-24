package com.althurdinok.fromandwith.ui.app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FromAndWithUiState(val darkTheme: Boolean = false)

@HiltViewModel
class FromAndWithViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FromAndWithUiState())

    val fromAndWithUiState = _uiState.asStateFlow()

    fun setThemeMode(darkTheme: Boolean) {
        _uiState.update { it.copy(darkTheme = darkTheme) }
    }

    fun getThemeMode(): Boolean = _uiState.value.darkTheme

}