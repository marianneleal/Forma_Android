package com.example.forma

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = "home") {
            HomeScreen(navController = navController, viewModel = HabitViewModel())
        }
        composable(
            route = "detail" + "/{habitId}",
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { entry ->
            DetailScreen(
                habitId = entry.arguments?.getInt("habitId"),
                navController = navController
            )
        }
    }
}
