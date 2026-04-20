package edu.emory.diabetes.education.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReminderScheduler{
    const val REQUEST_CODE = 2001
    private const val TAG = "reminder_scheduler"

    fun scheduleReminder(context: Context, durationMinutes: Int, route: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, CheckReminderReceiver::class.java).apply {
            putExtra(CheckReminderReceiver.EXTRA_ROUTE, route)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = System.currentTimeMillis()
        val triggerAtMillis = now + (durationMinutes * 60 * 1000L)

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Log.d(TAG, "===========================================")
        Log.d(TAG, "Scheduling reminder for $durationMinutes minute(s)")
        Log.d(TAG, "Current time  : ${sdf.format(Date(now))}")
        Log.d(TAG, "Reminder set for: ${sdf.format(Date(triggerAtMillis))}")
        Log.d(TAG, "===========================================")

        when {
            // API 31+ - explicit permission check for exact alarms
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                    Log.d(TAG, "Exact alarm scheduled successfully")
                } else {
                    Log.w(TAG, "Exact alarm permission NOT granted!")
                    Log.w(TAG, "Opening exact alarm settings for user to grant manually...")
                    // Fallback — when exact permission denied
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                }
            }
            else -> {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        }
    }

    fun cancelReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, CheckReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}