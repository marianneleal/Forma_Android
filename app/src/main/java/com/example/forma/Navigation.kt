package com.example.forma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.forma.data.HomeViewModel
import com.example.forma.screens.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = "home") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "detail" + "/{habitId}",
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            DetailScreen(
                habitId = it.arguments?.getLong("habitId") ?: -1,
                navController = navController
            )
        }
    }
}
