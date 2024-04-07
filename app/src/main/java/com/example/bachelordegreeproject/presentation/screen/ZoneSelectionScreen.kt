import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.R.string

@Composable
fun ZoneSelectionScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(id = string.selectZoneHeader),
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = Dark,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "PSO657A",
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
            color = Dark,
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fly))
                LottieAnimation(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = string.loadingZone),
                    fontSize = 20.sp,
                    color = Dark
                )
            }
        }
//        Box(
//            Modifier
//                .fillMaxSize()
//                .padding(vertical = 10.dp)
//        )
//        {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(vertical = 10.dp)
//            ) {
//                // Добавим два элемента в LazyColumn
//                item {
//                    Text(text = "Первый элемент")
//                }
//                item {
//                    Text(text = "Второй элемент")
//                }
//            }
//        }
    }
}
