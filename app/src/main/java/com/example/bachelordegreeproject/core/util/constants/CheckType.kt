package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.StringRes
import com.example.bachelordegreeproject.R

enum class CheckType(
    val code: Int,
    val value: String,
    @StringRes val infoTitle: Int,
    @StringRes val infoText: Int
) {
    PreflightCheck(0, "Pre", R.string.preFlightCheckTitle, R.string.preFlightCheckDesc),
    PostflightCheck(1, "Post", R.string.postFlightCheckTitle, R.string.postFlightCheckDesc),
    ExitCheck(2, "Exit", R.string.exitCheckTitle, R.string.exitCheckDesc)
}