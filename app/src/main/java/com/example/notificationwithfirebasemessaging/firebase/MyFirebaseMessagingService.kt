package com.example.notificationwithfirebasemessaging.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.notificationwithfirebasemessaging.notification.NotificationApp
import com.example.notificationwithfirebasemessaging.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MyFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "MyFirebaseMessaging"
    override fun onNewToken(p0: String) {
        Log.d(TAG, "onNewToken: $p0")
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification!=null)
            Log.d(TAG, "onMessageReceived from: ${remoteMessage.from}")
            Log.d(TAG, "onMessageReceived to: ${remoteMessage.to}")
            Log.d(TAG, "onMessageReceived collapseKey: ${remoteMessage.collapseKey}")
            Log.d(TAG, "onMessageReceived data: ${remoteMessage.data}")
            Log.d(TAG, "onMessageReceived messageId: ${remoteMessage.messageId}")
            Log.d(TAG, "onMessageReceived messageType: ${remoteMessage.messageType}")
            Log.d(TAG, "onMessageReceived originalPriority: ${remoteMessage.originalPriority}")
            Log.d(TAG, "onMessageReceived priority: ${remoteMessage.priority}")
            Log.d(TAG, "onMessageReceived rawData: ${remoteMessage.rawData}")
            Log.d(TAG, "onMessageReceived senderId: ${remoteMessage.senderId}")
            Log.d(TAG, "onMessageReceived sentTime: ${remoteMessage.sentTime}")
            Log.d(TAG, "onMessageReceived toIntent: ${remoteMessage.toIntent()}")
            val noti = remoteMessage.notification!!
            Log.d(TAG, "onMessageReceived bodyLocalizationArgs: ${noti.bodyLocalizationArgs}")
            Log.d(TAG, "onMessageReceived bodyLocalizationKey: ${noti.bodyLocalizationKey}")
            Log.d(TAG, "onMessageReceived channelId: ${noti.channelId}")
            Log.d(TAG, "onMessageReceived clickAction: ${noti.clickAction}")
            Log.d(TAG, "onMessageReceived color: ${noti.color}")
            Log.d(TAG, "onMessageReceived defaultLightSettings: ${noti.defaultLightSettings}")
            Log.d(TAG, "onMessageReceived defaultSound: ${noti.defaultSound}")
            Log.d(TAG, "onMessageReceived defaultVibrateSettings: ${noti.defaultVibrateSettings}")
            Log.d(TAG, "onMessageReceived eventTime: ${noti.eventTime}")
            Log.d(TAG, "onMessageReceived icon: ${noti.icon}")
            Log.d(TAG, "onMessageReceived imageUrl: ${noti.imageUrl}")
            Log.d(TAG, "onMessageReceived lightSettings: ${noti.lightSettings}")
            Log.d(TAG, "onMessageReceived link: ${noti.link}")
            Log.d(TAG, "onMessageReceived localOnly: ${noti.localOnly}")
            Log.d(TAG, "onMessageReceived notificationCount: ${noti.notificationCount}")
            Log.d(TAG, "onMessageReceived notificationPriority: ${noti.notificationPriority}")
            Log.d(TAG, "onMessageReceived sound: ${noti.sound}")
            Log.d(TAG, "onMessageReceived sticky: ${noti.sticky}")
            Log.d(TAG, "onMessageReceived tag: ${noti.tag}")
            Log.d(TAG, "onMessageReceived ticker: ${noti.ticker}")
            Log.d(TAG, "onMessageReceived titleLocalizationArgs: ${noti.titleLocalizationArgs}")
            Log.d(TAG, "onMessageReceived titleLocalizationKey: ${noti.titleLocalizationKey}")
            Log.d(TAG, "onMessageReceived vibrateTimings: ${noti.vibrateTimings}")
            Log.d(TAG, "onMessageReceived visibility: ${noti.visibility}")
            showNotification(noti.title!!,noti.body!!,getImageBitmap(noti.imageUrl.toString()))

        }

    private fun getImageBitmap(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.doInput = true
            BitmapFactory.decodeStream(httpsURLConnection.inputStream)
        }catch (e:Exception){
            null
        }
    }

    private fun showNotification(title:String,content:String,image:Bitmap?){
        val notification = NotificationApp.Builder(this)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_sports_baseball_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.purple_500))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setLights(Color.BLUE,500,200)
            .setVibrate(longArrayOf(0,500,500,500))
        if(image!=null) notification.setBigPictureStyle(image,image,title,content,true)
            .setLargeIcon(image)
        notification.notify(1)
    }



}
