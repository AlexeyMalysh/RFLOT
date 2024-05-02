package com.example.bachelordegreeproject.presentation.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bachelordegreeproject.core.util.constants.EquipStatus
import com.example.bachelordegreeproject.domain.models.EquipState

@Composable
fun AirplaneSeats(equips: List<EquipState>) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(equips) { equip ->
           SeatItem(equip)
        }
    }
}

@Preview
@Composable
fun AirplaneSeatsDemo() {
    val equip: List<EquipState> = listOf(
        EquipState("A1", EquipStatus.OK),
        EquipState("A2", EquipStatus.UNKNOWN),
        EquipState("A3", EquipStatus.UNKNOWN),
        EquipState("A4", EquipStatus.UNKNOWN),
        EquipState("B1", EquipStatus.UNKNOWN),
        EquipState("B2", EquipStatus.UNKNOWN),
        EquipState("B3", EquipStatus.UNKNOWN),
        EquipState("B4", EquipStatus.UNKNOWN),
        EquipState("C1", EquipStatus.UNKNOWN),
        EquipState("C2", EquipStatus.UNKNOWN),
        EquipState("C3", EquipStatus.UNKNOWN),
        EquipState("C4", EquipStatus.UNKNOWN),
        EquipState("D1", EquipStatus.DateFail),
        EquipState("D2", EquipStatus.OK),
        EquipState("D3", EquipStatus.OK),
        EquipState("D4", EquipStatus.OK),
    )
    AirplaneSeats(equips = equip)
}
