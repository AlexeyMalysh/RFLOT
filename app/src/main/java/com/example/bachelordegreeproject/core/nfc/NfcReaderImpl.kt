package com.example.bachelordegreeproject.core.nfc

import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.bachelordegreeproject.R
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.converts.toHex
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val SCAN_REPEAT_DELAY = 700
@Singleton
class NfcReaderImpl @Inject constructor() : NfcReader, NfcAdapter.ReaderCallback {
    private var activity: Activity? = null

    private var nfcAdapter: NfcAdapter? = null

    private val _rfidSharedFlow: MutableSharedFlow<RfidStatus> = MutableSharedFlow(replay = 0, extraBufferCapacity = 1)

    override val rfidIdSharedFlow: SharedFlow<RfidStatus>
        get() = _rfidSharedFlow.asSharedFlow()

    override fun initNfcReader(activity: Activity) {
        this.activity = activity
    }

    override fun startReadNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null || (nfcAdapter?.isEnabled == false)) {
            _rfidSharedFlow.tryEmit(RfidStatus.Error(R.string.nfcReaderModuleNotAvailable))
        } else {
            enableReadTagByOnTagDiscovered()
        }
    }

    override fun stopReadNfc() {
        nfcAdapter?.disableReaderMode(activity)
        nfcAdapter?.disableForegroundDispatch(activity)
        nfcAdapter = null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun resolveNfcIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == action ||
            NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                Timber.d("resolveIntent messages = $messages")
                messages.forEach {
                    Timber.d("resolveIntent message = $it")
                }
            }
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        val scanResult = if (tag != null) {
            val id = toHex(tag.id)
            RfidStatus.Success(id)
        } else {
            RfidStatus.Failure(R.string.nfcReaderTagIsNullFailure)
        }
        _rfidSharedFlow.tryEmit(scanResult)
    }

    private fun enableReadTagByOnTagDiscovered() {
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, SCAN_REPEAT_DELAY)

        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            options
        )
    }
}
