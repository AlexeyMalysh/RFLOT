package com.example.bachelordegreeproject.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.example.bachelordegreeproject.R
import timber.log.Timber

class RingtoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { action ->
            if (action == INTENT_ACTION) {
                try {
                    @RawRes val soundRes = R.raw.sound_alarm
                    MediaPlayer.create(context, soundRes).apply {
                        setOnCompletionListener { mp -> mp.release() }
                        start()
                    }
                } catch (exception: Exception) {
                    Timber.e("Error starting sound: $exception")
                }
            }
        }
    }

    companion object {
        const val INTENT_ACTION = "com.example.bachelordegreeproject.RINGTONES"
    }
}
