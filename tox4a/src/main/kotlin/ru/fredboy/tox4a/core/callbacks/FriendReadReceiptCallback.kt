package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber

fun interface FriendReadReceiptCallback {

    fun friendReadReceipt(
        toxFriendNumber: ToxFriendNumber,
        messageId: Int
    )

}