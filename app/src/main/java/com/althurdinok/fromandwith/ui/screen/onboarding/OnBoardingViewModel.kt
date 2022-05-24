package com.althurdinok.fromandwith.ui.screen.onboarding

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.althurdinok.fromandwith.data.OnBoardingData
import com.althurdinok.fromandwith.data.repository.onborading.OnBoardingRepository
import com.althurdinok.fromandwith.ui.navigation.app.AppDestinationRoute
import com.althurdinok.fromandwith.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val onBoardingDataEmitter = MutableLiveData<List<OnBoardingData>>()
    val onBoardingData: LiveData<List<OnBoardingData>> = onBoardingDataEmitter

    init {
        loadOnBoardingData()
    }

    fun finishBoardingScreen(context: Context, navController: NavController) {
        context.getSharedPreferences(
            Constants.SharedPreferences.SP_NAME,
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(Constants.SharedPreferences.IS_FIRST_TIME_LAUNCH, false)
            commit()
        }
        navToLoginOrSignup(navController)
    }

    private fun loadOnBoardingData() {
        viewModelScope.launch {
            onBoardingRepository.getOnBoardingDataset().subscribe {
                onBoardingDataEmitter.value = it
            }
        }
    }

    private fun navToLoginOrSignup(navController: NavController) {
        navController.navigate(AppDestinationRoute.LoginOrSignup.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}