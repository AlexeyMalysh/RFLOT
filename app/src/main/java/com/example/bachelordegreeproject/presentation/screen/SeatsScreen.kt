package com.example.bachelordegreeproject.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AirplaneSeats(seats: List<String>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(seats.windowed(4, 4, true)) { rowSeats ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                rowSeats.forEach { seat ->
                    SeatItem(seat)
                }
            }
        }
    }
}

@Composable
fun SeatItem(seat: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .background(Color.Gray, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = seat,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun AirplaneSeatsDemo() {
    val seats = listOf("A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4")
    AirplaneSeats(seats = seats)
}

