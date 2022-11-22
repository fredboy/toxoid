package ru.fredboy.toxoid.tox

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.activity.MainActivity
import javax.inject.Inject
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fredboy.toxoid.clean.data.source.intent.*
import ru.fredboy.toxoid.tox.api.ToxApiHandler


@AndroidEntryPoint
class ToxService : Service() {

    @Inject
    lateinit var useCases: ToxServiceUseCases

    @Inject
    lateinit var toxThread: ToxThread

    @Inject
    lateinit var toxApiHandler: ToxApiHandler

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val action = intent?.action ?: throw IllegalArgumentException("No action found")

        when {
            action == ACTION_INIT_TOX_SERVICE -> onInitAction()
            action.startsWith(API_ACTION_PREFIX) -> toxApiHandler.handleAction(
                action = action,
                data = intent.extras ?: throw IllegalArgumentException("No extras found"),
            )
        }

        return START_STICKY
    }

    private fun onInitAction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundOreo()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        toxThread.startThread()
    }

    override fun onDestroy() {
        super.onDestroy()
        toxThread.interruptThread()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundOreo() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val channelId = getString(R.string.foreground_service_notification_channel_id)
        val channelName = getString(R.string.foreground_service_notification_channel_name)
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.apply {
            description = getString(R.string.foreground_service_notification_channel_description)
            setShowBadge(false)
        }

        notificationManager.createNotificationChannel(channel)

        val pendingIntent = Intent(this, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification: Notification = Notification
            .Builder(this, channel.id)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.toxoid_is_running))
            .setContentIntent(pendingIntent)
            .setTicker(getString(R.string.foreground_service_notification_ticker))
            .build()

        startForeground(R.id.foreground_service_notification_id, notification)

        subscribeOnSelfConnectionStatus(notificationManager)
    }

    private fun subscribeOnSelfConnectionStatus(notificationManager: NotificationManager) {
        CoroutineScope(Dispatchers.Main).launch {
            useCases.getSelfConnectionStatusFlow()
                .collect { connection ->
                    val currentUserName = useCases.getCurrentUser()?.name.toString()

                    val notification: Notification = Notification
                        .Builder(
                            this@ToxService,
                            getString(R.string.foreground_service_notification_channel_id)
                        )
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("Toxoid status: ${connection.name}. User name: $currentUserName")
                        .setTicker(getString(R.string.foreground_service_notification_ticker))
                        .setSmallIcon(R.drawable.art_welcome)
                        .build()

                    notificationManager.notify(
                        R.id.foreground_service_notification_id,
                        notification
                    )
                }
        }
    }
}