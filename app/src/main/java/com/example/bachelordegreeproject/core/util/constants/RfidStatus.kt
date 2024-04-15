package com.example.bachelordegreeproject.core.util.constants

import androidx.annotation.StringRes

sealed class RfidStatus {
    data class Success(val rfid: String) : RfidStatus()

    data class Failure(@StringRes val failure: Int) : RfidStatus()

    data class Error(@StringRes val error: Int) : RfidStatus()

    data object Idle : RfidStatus()
}
