package ru.fredboy.toxoid.tox.api.methods

import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceResolveFriendNumberArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResolveFriendNumberResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey

class ResolveFriendNumberMethod(
    args: ToxServiceResolveFriendNumberArgs
) : ToxServiceApiMethod<ToxServiceResolveFriendNumberArgs>(
    args
) {
    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        return try {
            val friendPublicKeyBytes = toxCore.getFriendPublicKey(ToxFriendNumber(args.friendNumber))
            ToxServiceResolveFriendNumberResult(ToxPublicKey(friendPublicKeyBytes.value))
        } catch (e: Exception) {
            ToxServiceErrorResult(e)
        }
    }
}