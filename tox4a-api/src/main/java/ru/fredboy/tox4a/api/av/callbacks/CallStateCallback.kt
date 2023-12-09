package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.av.data.enums.ToxavFriendCallState
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import java.util.EnumSet

fun interface CallStateCallback {

    fun callState(friendNumber: ToxFriendNumber, callState: EnumSet<ToxavFriendCallState>)

}