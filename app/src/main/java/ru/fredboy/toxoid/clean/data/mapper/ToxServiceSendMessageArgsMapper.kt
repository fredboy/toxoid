package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.intent.ToxServiceSendMessageArgs
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey
import javax.inject.Inject

class ToxServiceSendMessageArgsMapper @Inject constructor() {

    fun map(recipientPublicKey: ToxPublicKey, message: Message): ToxServiceSendMessageArgs {
        return ToxServiceSendMessageArgs(
            recipientPublicKeyBytes = recipientPublicKey.bytes,
            messageBytes = message.text.toByteArray(),
            timestamp = message.date.time,
        )
    }

}