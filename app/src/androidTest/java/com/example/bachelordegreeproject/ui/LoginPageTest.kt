package com.example.bachelordegreeproject.ui

import androidx.activity.compose.setContent
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.presentation.MainActivity
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.screen.LoginPage
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginPageTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @MockK
    lateinit var mockNavController: NavController

    @MockK
    lateinit var mockViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockViewModel.rfidStatus } returns MutableLiveData()
        every { mockViewModel.authResult } returns MutableLiveData()
    }

    @Test
    fun testLoginSuccess() {
        val rfidStatus = RfidStatus.Success("some_rfid_code")
        val authResult = UiState.Success("")

        activityRule.scenario.onActivity {
            it.setContent {
                LoginPage(navController = mockNavController, viewModel = mockViewModel)
            }
        }

        (mockViewModel.rfidStatus as MutableLiveData).value = rfidStatus
        (mockViewModel.authResult as MutableLiveData).value = authResult

        verify { mockNavController.navigate(Screen.PlaneAuthScreen.route) }
    }
}
