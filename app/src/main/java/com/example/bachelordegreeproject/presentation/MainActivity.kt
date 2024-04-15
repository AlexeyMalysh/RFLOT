package com.example.bachelordegreeproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.presentation.route.Route
import com.example.bachelordegreeproject.presentation.theme.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var nfcReader: NfcReader
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcReader.initNfcReader(this)
        ViewCompat.getWindowInsetsController(window.decorView)!!
            .hide(WindowInsetsCompat.Type.systemBars())
        setContent {
            Route()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcReader.startReadNfc()
    }

    override fun onPause() {
        super.onPause()
        nfcReader.stopReadNfc()
    }
}
