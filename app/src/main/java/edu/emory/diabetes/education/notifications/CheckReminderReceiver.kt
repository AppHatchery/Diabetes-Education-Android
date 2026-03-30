package edu.emory.diabetes.education.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.emory.diabetes.education.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckReminderReceiver : BroadcastReceiver() {


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val route = intent.getStringExtra(EXTRA_ROUTE) ?: ""
        showCheckNotification(context, route)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showCheckNotification(context: Context, route: String) {
        val channelId = "check_reminder_channel"

        // Create notification channel (required API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Check Reminders",
                NotificationManager.IMPORTANCE_HIGH  // HIGH = time-sensitive
            ).apply {
                description = "Reminders to check blood sugar and ketones"
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableVibration(true)
                setBypassDnd(true) // time-sensitive: bypasses Do Not Disturb
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // Tap action — opens app when notification is tapped
        val tapIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(EXTRA_ROUTE, route)
            }


        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.im_clock)
            .setContentTitle("Time to Check!")
            .setContentText("Time to test your blood sugar and ketones")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Time to test your blood sugar and ketones")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .build()

        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val EXTRA_ROUTE     = "extra_reminder_route"
    }
}