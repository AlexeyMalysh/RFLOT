package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.presentation.theme.Dark

@Composable
fun AdditionalCheckInfoContent(infoTitle: String, infoText: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = infoTitle,
            style = MaterialTheme.typography.headlineMedium,
            color = Dark,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = infoText,
            style = MaterialTheme.typography.headlineSmall,
            color = Dark,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(36.dp))

        GradientButton(
            onClickAction = {
                onClick()
            },
            gradientColors = UIConst.gradientColor,
            cornerRadius = UIConst.gradientCornerRadius,
            nameButton = stringResource(id = string.positiveActionButtonTitle),
            roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))
    }
}
