package com.example.bachelordegreeproject.core.nfc

import android.app.Activity
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import kotlinx.coroutines.flow.SharedFlow

interface NfcReader {
    val rfidIdSharedFlow: SharedFlow<RfidStatus>
    fun initNfcReader(activity: Activity)
    fun startReadNfc()
    fun stopReadNfc()
}
