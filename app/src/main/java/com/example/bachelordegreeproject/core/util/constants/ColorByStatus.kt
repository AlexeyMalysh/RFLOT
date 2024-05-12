package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.bachelordegreeproject.R

enum class ColorByStatus(
    val value: Int,
    @StringRes val description: Int,
    @ColorRes val color: Color
) {
    Idle(-1, R.string.colorByStatusIdle, Color.White),
    OK(0, R.string.colorByStatusOk, Color.Green),
    DataFail(3, R.string.colorByStatusDataFail, Color.Red),
    None(1, R.string.colorByStatusNone, Color.Gray),
    NotFound(2, R.string.colorByStatusNotFound, Color.Blue),
    DataMont(4, R.string.colorByStatusDataMonth, Color.Yellow),
}
