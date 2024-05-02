package com.example.bachelordegreeproject.presentation.screen

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
import com.example.bachelordegreeproject.presentation.component.AdditionalCheckInfoContent
import com.example.bachelordegreeproject.presentation.component.BottomSheet
import com.example.bachelordegreeproject.presentation.component.FlightCheckCard
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.theme.Dark

@Composable
fun FlightCheckScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedType by remember { mutableStateOf<CheckType?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(64.dp))
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
                        navController.navigate(
                            Screen.PlaneAuthScreen.withArgs(CheckType.PreflightCheck.value)
                        )
                    })
                FlightCheckCard(
                    stringResource(id = CheckType.PostflightCheck.infoTitle),
                    CheckType.PostflightCheck,
                    selectedType = selectedType,
                    onClickedInfo = {
                        selectedType = it
                    },
                    onClick = {
                        navController.navigate(
                            Screen.PlaneAuthScreen.withArgs(CheckType.PostflightCheck.value)
                        )
                    })
                FlightCheckCard(
                    stringResource(id = CheckType.ExitCheck.infoTitle),
                    CheckType.ExitCheck,
                    selectedType = selectedType,
                    onClickedInfo = {
                        selectedType = it
                    },
                    onClick = {
                        navController.navigate(
                            Screen.PlaneAuthScreen.withArgs(CheckType.ExitCheck.value)
                        )
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
