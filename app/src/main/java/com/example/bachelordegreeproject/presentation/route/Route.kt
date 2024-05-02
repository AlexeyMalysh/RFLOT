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
import com.example.bachelordegreeproject.presentation.screen.CheckPointsScreen
import com.example.bachelordegreeproject.presentation.screen.FlightCheckScreen
import com.example.bachelordegreeproject.presentation.screen.LoginPage
import com.example.bachelordegreeproject.presentation.screen.PlaneAuthScreen
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel

@Composable
fun Route() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route, builder = {
        composable(Screen.LoginScreen.route, content = {
            MaterialTheme {
                LoginPage(navController = navController, viewModel)
            }
        })
        composable(
            "${Screen.PlaneAuthScreen.route}/{checkType}",
            arguments = listOf(navArgument("checkType") { type = NavType.StringType }),
            content = { backStackEntry ->
                AppTopBar(navController) {
                    MaterialTheme {
                        PlaneAuthScreen(
                            navController = navController,
                            viewModel = viewModel,
                            backStackEntry.arguments?.getString("checkType").toString()
                        )
                    }
                }
            })
        composable(route = Screen.FlightCheckScreen.route,
            content = {
                AppTopBar(navController) {
                    MaterialTheme {
                        FlightCheckScreen(
                            navController = navController
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
                            viewModel = viewModel,
                            backStackEntry.arguments?.getString("planeId").toString()
                        )
                    }
                }
            })

        composable(route = "${Screen.CheckPointsScreen.route}/{zoneName}",
            arguments = listOf(navArgument("zoneName") { type = NavType.StringType }),
            content = { backStackEntry ->
                AppTopBar(navController) {
                    MaterialTheme {
                        CheckPointsScreen(
                            navController = navController,
                            viewModel = viewModel,
                            backStackEntry.arguments?.getString("zoneName").toString()
                        )
                    }
                }
            })
    })
}
