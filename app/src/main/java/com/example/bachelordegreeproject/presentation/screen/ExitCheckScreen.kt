import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.domain.models.EquipInfo
import com.example.bachelordegreeproject.presentation.component.GradientButton
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.presentation.viewmodel.MainViewModel

@Composable
fun ExitCheckScreen(
    navController: NavController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val rfidInfo: RfidStatus? by viewModel.rfidStatus.observeAsState()
    val equipInfo: EquipInfo? by viewModel.equipExist.observeAsState()
    val playSound: Intent? by viewModel.playSound.observeAsState()
    val scanComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scan_animation))
    val warningComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.warning_animation))
    when (rfidInfo) {
        is RfidStatus.Success -> viewModel.checkExistEquipment()
        else -> {}
    }
    if (playSound != null) {
        LocalContext.current.sendBroadcast(playSound)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.exitCheckTitle),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            color = Dark,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        if (equipInfo == null) {
            LottieAnimation(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                composition = scanComposition,
                iterations = LottieConstants.IterateForever,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.exitCheckDescription),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = Dark,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
            GradientButton(
                onClickAction = {
                    viewModel.resetParams()
                    navController.popBackStack()
                },
                gradientColors = UIConst.gradientColor,
                cornerRadius = UIConst.gradientCornerRadius,
                nameButton = stringResource(id = R.string.endCheckPointsButtonTitle),
                roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
            )
        } else {
            LottieAnimation(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                composition = warningComposition,
                iterations = LottieConstants.IterateForever,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.equipmentName, equipInfo?.name ?: ""),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = Dark,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.equipmentPlace, equipInfo?.space ?: ""),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = Dark,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(60.dp))
            GradientButton(
                onClickAction = {
                    viewModel.resetParams()
                },
                gradientColors = UIConst.gradientColor,
                cornerRadius = UIConst.gradientCornerRadius,
                nameButton = stringResource(id = R.string.positiveActionButtonTitle),
                roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
    }
}
