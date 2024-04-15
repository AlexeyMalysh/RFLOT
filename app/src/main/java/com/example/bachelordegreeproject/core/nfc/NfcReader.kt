package com.example.bachelordegreeproject.core.nfc

import android.app.Activity
import android.content.Intent
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import kotlinx.coroutines.flow.StateFlow

interface NfcReader {
    val rfidIdStateFlow: StateFlow<RfidStatus>
    fun initNfcReader(activity: Activity)
    fun startReadNfc()
    fun stopReadNfc()
    fun resolveNfcIntent(intent: Intent)
}
