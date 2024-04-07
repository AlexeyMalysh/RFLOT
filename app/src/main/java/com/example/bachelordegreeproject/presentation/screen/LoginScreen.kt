package com.example.bachelordegreeproject.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.presentation.component.GradientButton
import com.example.bachelordegreeproject.presentation.component.SimpleOutlinedPasswordTextField
import com.example.bachelordegreeproject.presentation.component.SimpleOutlinedTextFieldSample
import com.example.bachelordegreeproject.presentation.route.Screen
import com.example.bachelordegreeproject.presentation.theme.Dark
import com.example.bachelordegreeproject.R.string

@Composable
fun LoginPage(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {


        Box(
            modifier = Modifier
                .align(Alignment.Center),
        ) {

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_page_animation))
            LottieAnimation(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(id = string.welcome),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Dark,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SimpleOutlinedTextFieldSample()

                Spacer(modifier = Modifier.padding(3.dp))
                SimpleOutlinedPasswordTextField()

                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp

                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    onClickAction = {
                        navController.navigate(Screen.PlaneAuthScreen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = stringResource(id = string.loginActionButtonTitle),
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
                )

                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = stringResource(id = string.scanRfidLogin),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = Dark,
                )

                Spacer(modifier = Modifier.padding(20.dp))

            }
        }

    }
}
