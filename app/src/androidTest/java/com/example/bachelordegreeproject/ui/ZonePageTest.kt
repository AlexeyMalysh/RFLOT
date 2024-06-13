package com.example.bachelordegreeproject.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bachelordegreeproject.presentation.MainActivity
import com.example.bachelordegreeproject.presentation.screen.LoginPage
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel
import io.mockk.impl.annotations.MockK
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginPageInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @MockK
    lateinit var mockViewModel: MainViewModel

    @MockK
    lateinit var mockNavController: NavController

    @Test
    fun loginPageTest() {
        composeTestRule.setContent {
            LoginPage(
                navController = mockNavController,
                viewModel = mockViewModel
            )
        }
        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Network failed").assertIsDisplayed()
    }
}
