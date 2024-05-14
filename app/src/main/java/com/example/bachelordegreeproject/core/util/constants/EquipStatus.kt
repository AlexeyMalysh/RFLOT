package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.ColorRes
import com.example.bachelordegreeproject.R

enum class EquipStatus(val value:Int, val status: String, @ColorRes val color: Int) {
    OK(0, "Ok", R.color.equipStatusOk),
    DateFail(1, "DateFail", R.color.equipStatusBad),
    UNKNOWN(2, "Unknown", R.color.equipStatusUnknown);

    companion object {
        fun findByValue(value: Int): EquipStatus {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}
