package com.example.bachelordegreeproject.core.nfc

import android.content.Intent
import kotlinx.coroutines.flow.StateFlow

interface NfcReader {
    val rfidIdStateFlow: StateFlow<String>
    fun startReadNfc()
    fun stopReadNfc()
    fun resolveNfcIntent(intent: Intent)
}
