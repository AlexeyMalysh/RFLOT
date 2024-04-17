package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.StringRes
import com.example.bachelordegreeproject.R

enum class CheckType(
    val value: String,
    @StringRes val infoTitle: Int,
    @StringRes val infoText: Int
) {
    PreflightCheck("Pre", R.string.preFlightCheckTitle, R.string.preFlightCheckDesc),
    PostflightCheck("Post", R.string.postFlightCheckTitle, R.string.postFlightCheckDesc),
    ExitCheck("Exit", R.string.exitCheckTitle, R.string.exitCheckDesc)
}