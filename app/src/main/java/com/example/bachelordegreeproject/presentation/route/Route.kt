package com.example.bachelordegreeproject.presentation.route

import ZoneSelectionScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bachelordegreeproject.presentation.component.AppTopBar
import com.example.bachelordegreeproject.presentation.screen.FlightCheckScreen
import com.example.bachelordegreeproject.presentation.screen.LoginPage
import com.example.bachelordegreeproject.presentation.screen.PlaneAuthScreen
import com.example.bachelordegreeproject.presentation.viewmodel.LoginViewModel
import com.example.bachelordegreeproject.presentation.viewmodel.PlaneAuthViewModel
import com.example.bachelordegreeproject.presentation.viewmodel.ZoneViewModel

@Composable
fun Route() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val planeAuthViewModel: PlaneAuthViewModel = hiltViewModel()
    val zoneViewModel: ZoneViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route, builder = {
        composable(Screen.LoginScreen.route, content = {
            MaterialTheme {
                LoginPage(navController = navController, loginViewModel)
            }
        })
        composable(Screen.PlaneAuthScreen.route, content = {
            AppTopBar(navController) {
                MaterialTheme {
                    PlaneAuthScreen(navController = navController, viewModel = planeAuthViewModel)
                }
            }
        })
        composable(route = "${Screen.FlightCheckScreen.route}/{planeId}",
            arguments = listOf(navArgument("planeId") { type = NavType.StringType }),
            content = { backStackEntry ->
                AppTopBar(navController) {
                    MaterialTheme {
                        FlightCheckScreen(
                            navController = navController,
                            viewModel = planeAuthViewModel,
                            backStackEntry.arguments?.getString("planeId").toString()
                        )
                    }
                }
            })
        composable(route = "${Screen.ZoneSelectionScreen.route}/{planeId}",
            arguments = listOf(navArgument("planeId") { type = NavType.StringType }),
            content = { backStackEntry ->
                AppTopBar(navController) {
                    MaterialTheme {
                        ZoneSelectionScreen(
                            navController = navController,
                            viewModel = zoneViewModel,
                            backStackEntry.arguments?.getString("planeId").toString()
                        )
                    }
                }
            })
    })
}
