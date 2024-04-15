package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private const val DELAY_CLOSE_TOAST = 2_000L

@Composable
fun CustomToast(
    message: String,
    duration: Long = DELAY_CLOSE_TOAST,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    isClosing: Boolean = true
) {
    var toastVisible by remember { mutableStateOf(true) }
    if (toastVisible) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = message,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    if (isClosing) {
        LaunchedEffect(true) {
            delay(duration)
            toastVisible = false
        }
    }
}
