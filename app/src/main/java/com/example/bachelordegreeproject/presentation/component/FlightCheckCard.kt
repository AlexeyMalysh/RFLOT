package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bachelordegreeproject.core.util.constants.CheckType
import com.example.bachelordegreeproject.presentation.theme.Dark

@Composable
fun FlightCheckCard(
    title: String,
    type: CheckType,
    selectedType: CheckType?,
    onClickedInfo: (CheckType?) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Dark
            )
            IconButton(onClick =
            {
                if (selectedType != type) onClickedInfo(type)
            }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                )
            }
        }
    }
}
