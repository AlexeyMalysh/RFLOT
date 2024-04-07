package com.example.bachelordegreeproject.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
fun FlightCheckScreen(navController: NavController, modifier: Modifier = Modifier) {
    var selectedType by remember { mutableStateOf<CheckType?>(null) }

    val infoTitle = when (selectedType) {
        CheckType.PreflightCheck -> stringResource(id = string.preFlightCheckTitle)
        CheckType.PostflightCheck -> stringResource(id = string.postFlightCheckTitle)
        CheckType.ExitCheck -> stringResource(id = string.exitCheckTitle)
        else -> ""
    }

    val infoText = when (selectedType) {
        CheckType.PreflightCheck -> stringResource(id = string.preFlightCheckDesc)
        CheckType.PostflightCheck -> stringResource(id = string.postFlightCheckDesc)
        CheckType.ExitCheck -> stringResource(id = string.exitCheckDesc)
        else -> ""
    }

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
                stringResource(id = string.preFlightCheckTitle),
                CheckType.PreflightCheck,
                selectedType = selectedType,
                onClickedInfo = {
                    selectedType = it
                },
                onClick = {
                    navController.navigate(Screen.ZoneSelectionScreen.route)
                })
            FlightCheckCard(
                stringResource(id = string.postFlightCheckTitle),
                CheckType.PostflightCheck,
                selectedType = selectedType,
                onClickedInfo = {
                    selectedType = it
                },
                onClick = {

                })
            FlightCheckCard(
                stringResource(id = string.exitCheckTitle),
                CheckType.ExitCheck,
                selectedType = selectedType,
                onClickedInfo = {
                    selectedType = it
                },
                onClick = {

                })

            if (selectedType != null) {
                BottomSheet(onDismiss = { selectedType = null }) {
                    AdditionalCheckInfoContent(infoTitle = infoTitle, infoText = infoText) {
                        selectedType = null
                    }
                }

            }
        }
    }
}
