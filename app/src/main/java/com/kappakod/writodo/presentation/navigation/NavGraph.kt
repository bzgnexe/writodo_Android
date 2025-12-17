package com.kappakod.writodo.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.kappakod.writodo.presentation.ui.splash.AppSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappakod.writodo.presentation.ui.home.AppHomeScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {

            composable(route = Screen.Splash.route) {
                AppSplashScreen(navController = navController)
            }

            composable(route = Screen.Home.route) {
                AppHomeScreen()
            }
    }

}



