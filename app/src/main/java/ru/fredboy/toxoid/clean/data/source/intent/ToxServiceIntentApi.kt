package ru.fredboy.toxoid.clean.data.source.intent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceGetOwnAddressArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceGetOwnAddressResult
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceSendMessageArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceAddFriendResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceSendMessageResult
import ru.fredboy.toxoid.clean.domain.model.*
import ru.fredboy.toxoid.tox.ToxService
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

@Singleton
class ToxServiceIntentApi @Inject constructor(
    @ApplicationContext private val context: Context,
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

    suspend fun getOwnAddress(): ToxAddress = suspendCoroutine { continuation ->
        val resultReceiver = createResultReceiver(
            continuation = continuation,
            resultType = ToxServiceGetOwnAddressResult::class.java
        ) { result -> result.address }

        val intent = createIntent(ACTION_GET_OWN_ADDRESS) {
            putParcelable(
                ToxServiceGetOwnAddressArgs.PARCEL_KEY,
                ToxServiceGetOwnAddressArgs(resultReceiver = resultReceiver)
            )
        }

        context.startService(intent)
    }

    suspend fun addFriend(friendRequest: FriendRequest) = suspendCoroutine<Unit> { continuation ->
        val resultReceiver = createResultReceiver(
            continuation = continuation,
            resultType = ToxServiceAddFriendResult::class.java
        ) { }

        val intent = createIntent(ACTION_ADD_FRIEND) {
            val args = ToxServiceAddFriendArgs(
                friendAddressBytes = friendRequest.friendAddress.bytes,
                messageBytes = friendRequest.message.toByteArray(),
                resultReceiver = resultReceiver,
            )

            putParcelable(ToxServiceAddFriendArgs.PARCEL_KEY, args)
        }

        context.startService(intent)
    }

    suspend fun sendMessage(recipientPublicKey: ToxPublicKey, message: Message) =
        suspendCoroutine<Unit> { continuation ->
            val resultReceiver = createResultReceiver(
                continuation = continuation,
                resultType = ToxServiceSendMessageResult::class.java
            ) { Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show() }

            val intent = createIntent(ACTION_SEND_MESSAGE) {
                val args = ToxServiceSendMessageArgs(
                    recipientPublicKeyBytes = recipientPublicKey.bytes,
                    messageBytes = message.text.toByteArray(),
                    timestamp = message.date.time,
                    resultReceiver = resultReceiver
                )

                putParcelable(ToxServiceSendMessageArgs.PARCEL_KEY, args)
            }

            context.startService(intent)
        }

    private fun createIntent(
        action: String,
        extrasBuilder: Bundle.() -> Unit,
    ): Intent {
        return Intent(context, ToxService::class.java)
            .apply {
                this.action = action
                putExtras(Bundle().apply(extrasBuilder))
            }
    }

    private fun <T, ResultClass : ToxServiceResult> createResultReceiver(
        continuation: Continuation<T>,
        resultType: Class<ResultClass>,
        callback: (ResultClass) -> T
    ): ResultReceiver {
        return object : ResultReceiver(Handler(context.mainLooper)) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                val result = resultData?.getParcelable(EXTRA_RESULT, resultType)

                val continuationResult = when {
                    result == null -> Result.failure(Exception("No result fount: ${resultType.simpleName}"))
                    result.error != null -> Result.failure(result.error as Throwable)
                    else -> Result.success(callback(result))
                }

                continuation.resumeWith(continuationResult)
            }
        }
    }
}