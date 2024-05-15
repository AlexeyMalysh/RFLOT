package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bachelordegreeproject.presentation.theme.Dark

@Composable
fun ZoneCard(
    zoneName: String,
    reviewersName: List<String>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(36.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = zoneName,
                style = MaterialTheme.typography.bodyLarge,
                color = Dark
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End
            ) {
                var countReviews = 1
                reviewersName.forEach { name ->
                    Text(text = "$countReviews. ${converLongName(name)}")
                    countReviews++
                }
            }
        }
    }
}

fun converLongName(name: String): String = if (name.length > 10) {
    "${name.substring(10)}.."
} else name

