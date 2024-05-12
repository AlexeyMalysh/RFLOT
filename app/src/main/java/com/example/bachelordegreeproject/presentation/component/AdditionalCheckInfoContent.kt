package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bachelordegreeproject.R.string
import com.example.bachelordegreeproject.core.util.constants.UIConst
import com.example.bachelordegreeproject.presentation.theme.Dark

@Composable
fun AdditionalCheckInfoContent(
    infoTitle: String,
    infoContent: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = infoTitle,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Dark,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(26.dp))
        infoContent()
        Spacer(modifier = Modifier.height(26.dp))

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
