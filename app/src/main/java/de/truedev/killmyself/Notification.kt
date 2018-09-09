package de.truedev.killmyself

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import de.truedev.killmyself.NotificationReceiver.Companion.ID


fun notification(context: Context): Notification {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    return NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_smoking_rooms_24px)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
}

fun setUpAlarm(context: Context) {

    val notificationIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = NotificationReceiver.NOTIFICATION_TRIGGER
        putExtra(NotificationReceiver.NOTIFICATION_ID, ID)
        putExtra(NotificationReceiver.NOTIFICATION, notification(context))
    }
    val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 30 * 1000,
            /* AlarmManager.INTERVAL_DAY*/60 * 1000, pendingIntent)
}

fun notify(context: Context, notification: Notification, notificationId: Int = ID) {

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(notificationId, notification)
}

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            setUpAlarm(context)
        }
        if (intent.action == NOTIFICATION_TRIGGER) {

            notify(context,
                    intent.getParcelableExtra(NOTIFICATION),
                    intent.getIntExtra(NotificationReceiver.NOTIFICATION_ID, ID))
        }
    }

    companion object {

        const val NOTIFICATION_ID = "notification-id"

        const val ID = 123

        const val NOTIFICATION = "notification"

        const val NOTIFICATION_TRIGGER = "de.truedev.killmyself.NOTIFICATION_TRIGGERED"

    }
}