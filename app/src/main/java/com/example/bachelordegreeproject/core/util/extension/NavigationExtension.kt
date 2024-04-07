package com.example.bachelordegreeproject.core.util.extension

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)

    try {
        startActivity(intent)
    } catch (e: Exception) {
        toast("d")
    }
}
