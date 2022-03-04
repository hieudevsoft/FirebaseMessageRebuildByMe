package com.example.notificationwithfirebasemessaging

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notificationwithfirebasemessaging.notification.NotificationApp

class MyApplication: Application() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        NotificationApp.createChannel(applicationContext,NotificationManager.IMPORTANCE_HIGH)
    }
}