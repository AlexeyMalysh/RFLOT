package com.example.bachelordegreeproject.presentation.route

import ZoneSelectionScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bachelordegreeproject.presentation.component.AppTopBar
import com.example.bachelordegreeproject.presentation.screen.FlightCheckScreen
import com.example.bachelordegreeproject.presentation.screen.LoginPage
import com.example.bachelordegreeproject.presentation.screen.PlaneAuthScreen

@Composable
fun Route() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route, builder = {
        composable(Screen.LoginScreen.route, content = {
            MaterialTheme {
                LoginPage(navController = navController)
            }
        })
        composable(Screen.PlaneAuthScreen.route, content = {
            AppTopBar(navController) {
                MaterialTheme {
                    PlaneAuthScreen(navController = navController)
                }
            }
        })
        composable(Screen.FlightCheckScreen.route, content = {
            AppTopBar(navController) {
                MaterialTheme {
                    FlightCheckScreen(navController = navController)
                }
            }
        })
        composable(Screen.ZoneSelectionScreen.route, content = {
            AppTopBar(navController) {
                MaterialTheme {
                    ZoneSelectionScreen(navController = navController)
                }
            }
        })
    })
}
