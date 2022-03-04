package com.example.notificationwithfirebasemessaging.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationApp private constructor() {
    companion object {
        var CHANNEL_ID = "AppId"
        var CHANNEL_NAME = "Notification"
        var CHANNEL_DES = "Notification description"
        var RC_NOTIFICATION = 100
        fun createChannel(context: Context, importance: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                    .setFlags(AudioAttributes.FLAG_HW_AV_SYNC)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
                val notificationChannelBuilder =
                    NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                notificationChannelBuilder.description = CHANNEL_DES
                notificationChannelBuilder.importance = importance
                notificationChannelBuilder.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET
                notificationChannelBuilder.enableLights(true)
                notificationChannelBuilder.lightColor = NotificationCompat.DEFAULT_LIGHTS
                notificationChannelBuilder.enableVibration(true)
                notificationChannelBuilder.vibrationPattern = longArrayOf(0, 500, 500,500)
                notificationChannelBuilder.setSound(
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    audioAttributes
                )

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannelBuilder)
            }
        }
    }

    internal class Builder(private val context: Context) {
        private var notificationCompatBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)

        fun setContentTitle(title: String): Builder {
            apply {
                notificationCompatBuilder.setContentTitle(title)
                return this
            }

        }

        fun setContentText(text: String): Builder {
            apply {
                notificationCompatBuilder.setContentText(text)
                return this
            }
        }

        fun setSmallIcon(icon: Int): Builder {
            apply {
                notificationCompatBuilder.setSmallIcon(icon)
                return this
            }
        }

        fun setLargeIcon(bitmap: Bitmap): Builder {
            apply {
                notificationCompatBuilder.setLargeIcon(bitmap)
                return this
            }
        }

        fun setContentIntent(intent: Intent, flags: Int): Builder {
            apply {
                notificationCompatBuilder.setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        RC_NOTIFICATION,
                        intent,
                        flags
                    )
                )
                return this
            }
        }

        fun setAutoCancel(isCancel: Boolean): Builder {
            apply {
                notificationCompatBuilder.setAutoCancel(isCancel)
                return this
            }
        }

        fun addAction(icon: Int, textAction: String, intent: Intent, flags: Int): Builder {
            apply {
                notificationCompatBuilder.addAction(
                    icon,
                    textAction,
                    PendingIntent.getActivity(context, RC_NOTIFICATION, intent, flags)
                )
                return this
            }
        }

        fun setColor(color: Int): Builder {
            apply {
                notificationCompatBuilder.color = color
                return this
            }
        }

        fun setPriority(priority: Int): Builder {
            apply {
                notificationCompatBuilder.color = priority
                return this
            }
        }


        fun setBigPictureStyle(
            bitmapLargePicture: Bitmap? = null,
            bitmapLargeIcon: Bitmap? = null,
            bigContentTitle: String? = null,
            summaryText: String? = null,
            isShowBigPictureWhenCollapsed: Boolean? = null
        ): Builder {
            apply {
                val style = NotificationCompat.BigPictureStyle()
                if (bigContentTitle != null) style.setBigContentTitle(bigContentTitle)
                if (summaryText != null) style.setSummaryText(summaryText)
                if (isShowBigPictureWhenCollapsed != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    style.showBigPictureWhenCollapsed(isShowBigPictureWhenCollapsed)
                }
                if (bitmapLargePicture != null) style.bigPicture(bitmapLargePicture)
                if (bitmapLargeIcon != null) style.bigLargeIcon(bitmapLargeIcon)
                notificationCompatBuilder.setStyle(style)
                return this
            }

        }

        fun setBigTextStyle(
            bigContentTitle: String? = null,
            summaryText: String? = null,
            bigText: String? = null,
        ): Builder {
            apply {
                val style = NotificationCompat.BigTextStyle()
                if (bigContentTitle != null) style.setBigContentTitle(bigContentTitle)
                if (summaryText != null) style.setSummaryText(summaryText)
                if (bigText != null) style.bigText(bigText)
                notificationCompatBuilder.setStyle(style)
                return this
            }
        }

        fun setInBoxStyle(
            bigContentTitle: String? = null,
            summaryText: String? = null,
            listLine:List<String>,
        ): Builder {
            apply {
                val style = NotificationCompat.InboxStyle()
                if (bigContentTitle != null) style.setBigContentTitle(bigContentTitle)
                if (summaryText != null) style.setSummaryText(summaryText)
                listLine.forEach { style.addLine(it) }
                notificationCompatBuilder.setStyle(style)
                return this
            }

        }

        fun setSound(uri:Uri): Builder {
            apply {
                notificationCompatBuilder.setSound(uri)
                return this
            }
        }
        fun setLights(argbs:Int,onMs:Int,offMs:Int): Builder {
            apply {
                notificationCompatBuilder.setLights(argbs,onMs,offMs)
                return this
            }
        }


        fun setDecoratedCustomViewStyle(): Builder {
            apply {
                notificationCompatBuilder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
                return this
            }
        }

        fun setVibrate(vibs:LongArray): Builder {
            apply {
                notificationCompatBuilder.setVibrate(vibs)
                return this
            }
        }

        fun notify(id: Int) =
            NotificationManagerCompat.from(context).notify(id, notificationCompatBuilder.build())

    }

}