package ru.fredboy.toxoid.clean.data.source.intent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.data.mapper.ToxServiceAddFriendArgsMapper
import ru.fredboy.toxoid.clean.data.mapper.ToxServiceSendMessageArgsMapper
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.tox.ToxService
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class ToxServiceIntentApi @Inject constructor(
    @ApplicationContext private val context: Context,
    private val toxServiceSendMessageArgsMapper: ToxServiceSendMessageArgsMapper,
    private val toxServiceAddFriendArgsMapper: ToxServiceAddFriendArgsMapper,
) {

    fun initService() {
        val toxServiceIntent = Intent(context, ToxService::class.java)
        toxServiceIntent.action = ACTION_INIT_TOX_SERVICE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(toxServiceIntent)
        } else {
            context.startService(toxServiceIntent)
        }
    }

    fun addFriend(friendRequest: FriendRequest) {
        val intent = createIntent(
            action = ACTION_ADD_FRIEND,
            extrasBuilder = {
                val args = toxServiceAddFriendArgsMapper.map(friendRequest)
                putParcelable(TOX_API_ADD_FRIEND_ARGS, args)
            }
        )
        context.startService(intent)
    }

    fun sendMessage(recipientPublicKey: ToxPublicKey, message: Message) {
        val intent = createIntent(
            action = ACTION_SEND_MESSAGE,
            extrasBuilder = {
                val args = toxServiceSendMessageArgsMapper.map(recipientPublicKey, message)
                putParcelable(TOX_API_SEND_MESSAGE_ARGS, args)
            },
            callback = { Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT,).show() }
        )
        context.startService(intent)
    }

    private fun createIntent(
        action: String,
        extrasBuilder: Bundle.() -> Unit,
        callback: (Bundle?) -> Unit = {},
        onError: (Throwable) -> Unit = { e -> e.printStackTrace() }
    ): Intent {
        return Intent(context, ToxService::class.java)
            .apply {
                this.action = action
                putExtras(Bundle().apply(extrasBuilder))
                putExtra(EXTRA_CALLBACK, object : ResultReceiver(Handler()) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        when (resultCode) {
                            R.id.result_code_okay -> callback(resultData)
                            R.id.result_code_error -> onError(
                                resultData?.getSerializable(
                                    EXTRA_THROWABLE, Throwable::class.java
                                ) ?: Throwable()
                            )
                        }
                    }
                })
            }
    }

}