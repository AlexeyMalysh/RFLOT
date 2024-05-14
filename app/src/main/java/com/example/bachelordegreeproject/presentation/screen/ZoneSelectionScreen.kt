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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.domain.models.Zones
import com.example.bachelordegreeproject.presentation.component.CustomToast
import com.example.bachelordegreeproject.presentation.component.GradientButton
import com.example.bachelordegreeproject.presentation.component.ZoneCard
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel

@Composable
fun ZoneSelectionScreen(
    navController: NavController,
    viewModel: MainViewModel,
    planeId: String,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.getAllZones()
    }

    val zones: Result<Zones>? by viewModel.zoneList.observeAsState()
    val zoneName: UiState? by viewModel.zoneName.observeAsState()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        when (zoneName) {
            is UiState.Loading -> {
                CustomProgress()
            }

            is UiState.Success -> {
                navController.navigate(
                    Screen.CheckPointsScreen.withArgs(
                        (zoneName as UiState.Success).text ?: ""
                    )
                )
            }

            else -> {}
        }

        when (zones) {
            is Result.Loading -> {
                CustomProgress()
            }

            else -> {}
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = stringResource(id = string.selectZoneHeader),
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = planeId,
                fontSize = 16.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = Dark,
            )
            when (zones) {
                is Result.Fail -> {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomToast(
                            stringResource(id = string.networkFailedGetAllZones),
                            backgroundColor = Color.White,
                            textColor = Color.Red,
                            isClosing = false
                        )
                        GradientButton(
                            onClickAction = {
                                viewModel.getAllZones()
                            },
                            gradientColors = UIConst.gradientColor,
                            cornerRadius = UIConst.gradientCornerRadius,
                            nameButton = stringResource(id = string.repeat),
                            roundedCornerShape = RoundedCornerShape(
                                topStart = 30.dp,
                                bottomEnd = 30.dp
                            )
                        )
                    }
                }

                else -> {}
            }

            when (zones) {
                is Result.Success -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 30.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            if (zones is Result.Success) {
                                (zones as Result.Success<Zones>).value.zonesInfo.forEach { info ->
                                    item {
                                        ZoneCard(
                                            zoneName = info.name,
                                            reviewersName = info.reviewersName
                                        ) {
                                            viewModel.startCheckZone(info.idZone)
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {}
            }

        }
    }
}
