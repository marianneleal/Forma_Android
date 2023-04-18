package com.example.forma.screens

// sealed class only allows to create subclasses in the same file
sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object DetailScreen : Screen("detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
