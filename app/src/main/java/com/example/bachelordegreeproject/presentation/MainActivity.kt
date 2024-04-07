package com.example.bachelordegreeproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bachelordegreeproject.presentation.route.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.getWindowInsetsController(window.decorView)!!
            .hide(WindowInsetsCompat.Type.systemBars())
        setContent {
            Route()
        }
    }
}
