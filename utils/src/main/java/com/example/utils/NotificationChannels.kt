package com.example.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    const val MESSAGES_LOW_CHANEL_ID = "messagesLow"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context) {
        val name = "Low Messages"
        val channelDescriptions = "News messages"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(MESSAGES_LOW_CHANEL_ID, name, priority).apply {
            description = channelDescriptions
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}