package ru.fredboy.toxoid.clean.data.source.intent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.toxoid.clean.data.model.intent.args.*
import ru.fredboy.toxoid.clean.data.model.intent.result.*
import ru.fredboy.toxoid.clean.data.model.tox.ToxSaveData
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

    suspend fun getOwnAddress(): ToxAddress = suspendCoroutineOnMain { continuation ->
        val resultReceiver = createResultReceiver<ToxAddress, ToxServiceGetOwnAddressResult>(
            continuation = continuation,
        ) { result -> result.address }

        val intent = createIntent(
            action = ACTION_GET_OWN_ADDRESS,
            args = ToxServiceGetOwnAddressArgs(resultReceiver = resultReceiver)
        )

        context.startService(intent)
    }

    suspend fun addFriend(friendRequest: FriendRequest) =
        suspendCoroutineOnMain<ToxSaveData> { continuation ->
            val resultReceiver = createResultReceiver<ToxSaveData, ToxServiceAddFriendResult>(
                continuation = continuation,
            ) { result -> result.toxSaveData }

            val intent = createIntent(
                action = ACTION_ADD_FRIEND,
                args = ToxServiceAddFriendArgs(
                    friendAddressBytes = friendRequest.friendAddress.bytes,
                    messageBytes = friendRequest.message.toByteArray(),
                    resultReceiver = resultReceiver,
                )
            )

            context.startService(intent)
        }

    suspend fun sendMessage(recipientPublicKey: ToxPublicKey, message: Message) =
        suspendCoroutineOnMain<Unit> { continuation ->
            val resultReceiver = createResultReceiver<Unit, ToxServiceSendMessageResult>(
                continuation = continuation,
            ) {  }

            val intent = createIntent(
                action = ACTION_SEND_MESSAGE,
                args = ToxServiceSendMessageArgs(
                    recipientPublicKeyBytes = recipientPublicKey.bytes,
                    messageBytes = message.text.toByteArray(),
                    timestamp = message.date.time,
                    resultReceiver = resultReceiver
                )
            )

            context.startService(intent)
        }

    suspend fun resolveFriendNumber(friendNumber: Int): ToxPublicKey =
        suspendCoroutineOnMain { continuation ->
            val resultReceiver =
                createResultReceiver<ToxPublicKey, ToxServiceResolveFriendNumberResult>(
                    continuation = continuation,
                ) { result -> result.friendPublicKey }

            val intent = createIntent(
                action = ACTION_RESOLVE_FRIEND_NUMBER,
                args = ToxServiceResolveFriendNumberArgs(friendNumber, resultReceiver)
            )

            context.startService(intent)
        }

    private fun createIntent(action: String, args: ToxServiceArgs): Intent {
        return Intent(context, ToxService::class.java)
            .apply {
                this.action = action
                putExtras(Bundle().apply { putArgs(args) })
            }
    }

    private inline fun <reified T, reified ResultClass : ToxServiceResult> createResultReceiver(
        continuation: Continuation<T>,
        crossinline callback: (ResultClass) -> T
    ): ResultReceiver {
        return object : ResultReceiver(Handler(context.mainLooper)) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                val result = resultData?.getParcelable(EXTRA_RESULT, ToxServiceResult::class.java)

                val notFoundException by lazy {
                    Exception("No result found: ${ResultClass::class.java.simpleName}")
                }

                val continuationResult = when (result) {
                    null -> Result.failure(notFoundException)
                    is ToxServiceErrorResult -> Result.failure(result.error)
                    !is ResultClass -> Result.failure(notFoundException)
                    else -> Result.success(callback(result))
                }

                continuation.resumeWith(continuationResult)
            }
        }
    }

    private fun Bundle.putArgs(args: ToxServiceArgs) {
        putParcelable(EXTRA_ARGUMENTS, args)
    }

    private suspend inline fun <reified T>
            suspendCoroutineOnMain(crossinline block: (Continuation<T>) -> Unit) =
        withContext<T>(Dispatchers.Main) {
            suspendCoroutine { continuation -> block(continuation) }
        }
}