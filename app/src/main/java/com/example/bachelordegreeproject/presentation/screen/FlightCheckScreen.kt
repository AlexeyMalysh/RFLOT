package com.example.bachelordegreeproject.presentation.screen

import CustomProgress
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.CheckType
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.presentation.component.AdditionalCheckInfoContent
import com.example.bachelordegreeproject.presentation.component.BottomSheet
import com.example.bachelordegreeproject.presentation.component.CustomToast
import com.example.bachelordegreeproject.presentation.component.FlightCheckCard
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.presentation.viewmodel.PlaneAuthViewModel

@Composable
fun FlightCheckScreen(
    navController: NavController,
    viewModel: PlaneAuthViewModel,
    planeId: String,
    modifier: Modifier = Modifier
) {
    val authResult: UiState? by viewModel.authPlaneResult.observeAsState()
    var selectedType by remember { mutableStateOf<CheckType?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {

        when (authResult) {
            is UiState.Success -> {
                navController.navigate(Screen.ZoneSelectionScreen.withArgs(planeId))
                viewModel.resetParams()
            }

            is UiState.Loading -> {
                CustomProgress()
            }

            else -> {}
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            when (authResult) {
                is UiState.Error -> {
                    CustomToast(
                        stringResource(id = string.networkFailedStartSession),
                        backgroundColor = Color.White,
                        textColor = Color.Red,
                    )
                }

                else -> {}
            }
            Text(
                text = stringResource(id = string.selectCheckType),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                color = Dark,
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                FlightCheckCard(
                    stringResource(id = CheckType.PreflightCheck.infoTitle),
                    CheckType.PreflightCheck,
                    selectedType = selectedType,
                    onClickedInfo = {
                        selectedType = it
                    },
                    onClick = {
                        viewModel.authPlane(planeId, CheckType.PreflightCheck)
                    })
                FlightCheckCard(
                    stringResource(id = CheckType.PostflightCheck.infoTitle),
                    CheckType.PostflightCheck,
                    selectedType = selectedType,
                    onClickedInfo = {
                        selectedType = it
                    },
                    onClick = {
                        viewModel.authPlane(planeId, CheckType.PostflightCheck)
                    })
                FlightCheckCard(
                    stringResource(id = CheckType.ExitCheck.infoTitle),
                    CheckType.ExitCheck,
                    selectedType = selectedType,
                    onClickedInfo = {
                        selectedType = it
                    },
                    onClick = {
                        viewModel.authPlane(planeId, CheckType.ExitCheck)
                    })

                if (selectedType != null) {
                    BottomSheet(onDismiss = { selectedType = null }) {
                        AdditionalCheckInfoContent(
                            infoTitle = selectedType?.let { stringResource(it.infoTitle) } ?: "",
                            infoText = selectedType?.let { stringResource(it.infoText) } ?: ""
                        ) {
                            selectedType = null
                        }
                    }

                }
            }
        }
    }
}
