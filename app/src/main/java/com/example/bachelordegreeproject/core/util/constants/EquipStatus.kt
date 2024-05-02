package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.ColorRes
import com.example.bachelordegreeproject.R

enum class EquipStatus(val value: String, @ColorRes val color: Int) {
    OK("Ok", R.color.equipStatusOk),
    DateFail("DateFail", R.color.equipStatusBad),
    UNKNOWN("Unknown", R.color.equipStatusUnknown);

    companion object {
        fun findByValue(value: String): EquipStatus {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}
