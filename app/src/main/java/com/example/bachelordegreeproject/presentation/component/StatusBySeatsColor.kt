package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bachelordegreeproject.core.util.constants.ColorByStatus

@Composable
fun StatusBySeatsColor(colorItems: Array<ColorByStatus>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(colorItems) { colorItem ->
            ColorItemView(colorItem)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ColorItemView(colorItem: ColorByStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorItem.color,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = colorItem.description),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun ColorListExample() {
    StatusBySeatsColor(ColorByStatus.entries.toTypedArray())
}
