package com.example.bachelordegreeproject.presentation.route

sealed class Screen(
    val route: String
) {
    data object LoginScreen : Screen("LoginScreen")
    data object PlaneAuthScreen : Screen("PlaneAuthScreen")
    data object FlightCheckScreen : Screen("FlightCheckScreen")
    data object ZoneSelectionScreen : Screen("ZoneSelectionScreen")
    data object CheckPointsScreen : Screen("CheckPointsScreen")

    fun withArgs(vararg args: Boolean): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
