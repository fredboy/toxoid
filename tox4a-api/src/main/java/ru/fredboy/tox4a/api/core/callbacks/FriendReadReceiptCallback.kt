package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface FriendReadReceiptCallback {

    fun friendReadReceipt(
        toxFriendNumber: ToxFriendNumber,
        messageId: Int
    )

}