package com.example.bachelordegreeproject.core.nfc

import java.nio.charset.Charset
import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.bachelordegreeproject.core.util.converts.byteArrayToHexString
import com.example.bachelordegreeproject.core.util.converts.hexStringToByteArray
import com.example.bachelordegreeproject.core.util.converts.toHex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class NfcReaderImpl(private val activity: Activity) : NfcReader,
    NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null

    private val _rfidStateFlow: MutableStateFlow<String> = MutableStateFlow(String())

    override val rfidIdStateFlow: StateFlow<String>
        get() = _rfidStateFlow.asStateFlow()

    override fun startReadNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null || (nfcAdapter?.isEnabled == false)) {
            Timber.e("NFC is not available or enabled")
        } else {
            setupNfcReaderModeBackground()
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
        Timber.d("resolveIntent = $intent")

        val action = intent.action
        Timber.d("resolveIntent action = $action")
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == action ||
            NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                // Process the messages array.
                Timber.d("resolveIntent messages = $messages")
                messages.forEach {
                    Timber.d("resolveIntent message = $it")
                }
            }

            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            Timber.d("resolveIntent tag = $tag")
            tag?.let {
                Timber.d("resolveIntent tag.id = ${tag.id}")
                Timber.d("resolveIntent tag.id Hex = ${byteArrayToHexString(tag.id)}")
            }

            val uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            uid?.let {
                val uidHex = byteArrayToHexString(uid)
                Timber.d("resolveIntent uidHex = $uidHex")
            }
            Timber.d("resolveIntent uid = $uid")

            val aid = intent.getByteArrayExtra(NfcAdapter.EXTRA_AID)
            Timber.d("resolveIntent aid = $aid")

            val data = intent.getByteArrayExtra(NfcAdapter.EXTRA_DATA)
            Timber.d("resolveIntent data = $data")

            val secure = intent.getByteArrayExtra(NfcAdapter.EXTRA_SECURE_ELEMENT_NAME)
            Timber.d("resolveIntent secure = $secure")

            val message = intent.getByteArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            Timber.d("resolveIntent message = $message")

            val state = intent.getByteArrayExtra(NfcAdapter.EXTRA_ADAPTER_STATE)
            Timber.d("resolveIntent state = $state")

            val paymentPreferred =
                intent.getByteArrayExtra(NfcAdapter.EXTRA_PREFERRED_PAYMENT_CHANGED_REASON)
            Timber.d("resolveIntent paymentPreferred = $paymentPreferred")

            val readerDelay = intent.getByteArrayExtra(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY)
            Timber.d("resolveIntent readerDelay = $readerDelay")
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        Timber.d("onTagDiscovered: $tag")
        if (tag != null) {
            val id = toHex(tag.id)
            Timber.d("TAG ID: $id")
            _rfidStateFlow.tryEmit(id)

            var techs = ""
            tag.techList.forEach {
                techs = "$techs${if (techs.isNotEmpty()) "," else ""} ${it.split(".").last()}"
            }

            if (tag.techList.contains("android.nfc.tech.MifareClassic")) {
                readMifareClassicTag(tag)
                return
            }

            val isoDep = IsoDep.get(tag)
            try {
                isoDep.connect()
                val responseByte =
                    isoDep.transceive(hexStringToByteArray("00A4040007F0010203040506"))
                val responseString = String(responseByte)
                Timber.d("onTagDiscovered result: id: $id, techs: $techs, responseString: $responseString")
                isoDep.close()
            } catch (e: Exception) {
                Timber.d("onTagDiscovered error: $e")
            }
        }
    }

    private fun readMifareClassicTag(tag: Tag) {
        val mifareClassic = MifareClassic.get(tag)
        try {
            mifareClassic.connect()
            val sectorCount = mifareClassic.sectorCount
            for (sectorIndex in 0 until sectorCount) {
                val keyA = MifareClassic.KEY_DEFAULT
                val isAuthenticated = mifareClassic.authenticateSectorWithKeyA(sectorIndex, keyA)

                if (isAuthenticated) {
                    val blockCountInSector = mifareClassic.getBlockCountInSector(sectorIndex)
                    val firstBlockIndex = mifareClassic.sectorToBlock(sectorIndex)

                    for (blockOffset in 0 until blockCountInSector) {
                        val blockIndex = firstBlockIndex + blockOffset
                        val blockData = mifareClassic.readBlock(blockIndex)
                        val blockDataString = String(blockData, Charset.forName("UTF-8"))
                        Timber.d("Data at block $blockIndex: $blockDataString")
                    }
                } else {
                    Timber.e("Failed to authenticate sector $sectorIndex")
                }
            }
        } catch (e: Exception) {
            Timber.e("Error reading Mifare Classic tag: $e")
        } finally {
            if (mifareClassic.isConnected) {
                mifareClassic.close()
            }
        }
    }

    private fun enableReadTagByOnTagDiscovered() {
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    private fun setupNfcReaderModeBackground() {
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NFC_BARCODE,
            options
        )
    }
}
