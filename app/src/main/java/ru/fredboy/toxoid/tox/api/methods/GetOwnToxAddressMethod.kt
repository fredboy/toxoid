package ru.fredboy.toxoid.tox.api.methods

import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceGetOwnAddressArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceGetOwnAddressResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.domain.model.ToxAddress

class GetOwnToxAddressMethod(
    args: ToxServiceGetOwnAddressArgs,
) : ToxServiceApiMethod<ToxServiceGetOwnAddressArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        return ToxServiceGetOwnAddressResult(
            address = ToxAddress(toxCore.getAddress().value)
        )
    }

}