package ru.fredboy.toxoid.tox.api.methods

import android.os.ResultReceiver
import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult

typealias ToxServiceGenericApiMethod = ToxServiceApiMethod<ToxServiceArgs>

abstract class ToxServiceApiMethod<Args : ToxServiceArgs>(
    protected val args: Args,
) {

    val resultReceiver: ResultReceiver get() = args.resultReceiver

    abstract suspend fun execute(toxCore: ToxCore): ToxServiceResult

}