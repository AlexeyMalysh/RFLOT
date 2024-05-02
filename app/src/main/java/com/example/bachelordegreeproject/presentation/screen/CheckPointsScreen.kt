package com.example.bachelordegreeproject.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.EquipStatus
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.domain.models.EquipState
import com.example.bachelordegreeproject.presentation.component.AirplaneSeats
import com.example.bachelordegreeproject.presentation.component.CustomToast
import com.example.bachelordegreeproject.presentation.component.GradientButton
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel

@Composable
fun CheckPointsScreen(
    navController: NavController,
    viewModel: MainViewModel,
    zoneName: String,
    modifier: Modifier = Modifier
) {
    val rfidInfo: RfidStatus? by viewModel.rfidStatus.observeAsState()
    val equips: List<EquipState>? by viewModel.equipsList.observeAsState()

    when (rfidInfo) {
        is RfidStatus.Success -> viewModel.convertEquipsList()
        is RfidStatus.Failure -> CustomToast(stringResource(id = (rfidInfo as RfidStatus.Failure).failure))
        is RfidStatus.Error -> CustomToast(
            stringResource(id = (rfidInfo as RfidStatus.Error).error),
            backgroundColor = Color.White,
            textColor = Color.Red,
            isClosing = false
        )

        else -> {}
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = string.checkPointsTitle),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            color = Dark,
            fontWeight = Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = zoneName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            color = Dark
        )
        Spacer(modifier = Modifier.height(10.dp))
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scan_animation))
        LottieAnimation(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
        Spacer(modifier = Modifier.height(30.dp))

        AirplaneSeats(equips = equips ?: listOf())

        Spacer(modifier = Modifier.height(60.dp))

        GradientButton(
            onClickAction = {
                navController.popBackStack()
            },
            gradientColors = UIConst.gradientColor,
            cornerRadius = UIConst.gradientCornerRadius,
            nameButton = stringResource(id = string.endCheckPointsButtonTitle),
            roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
        )

        Spacer(modifier = Modifier.padding(20.dp))

    }
}
