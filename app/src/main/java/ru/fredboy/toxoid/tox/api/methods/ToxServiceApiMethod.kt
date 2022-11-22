package ru.fredboy.toxoid.tox.api.methods

import android.os.ResultReceiver
import im.tox.tox4j.core.ToxCore
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult

typealias ToxServiceGenericApiMethod = ToxServiceApiMethod<ToxServiceArgs, ToxServiceResult>

abstract class ToxServiceApiMethod<Args : ToxServiceArgs, Result : ToxServiceResult>(
    protected val args: Args,
) {

    val resultReceiver: ResultReceiver get() = args.resultReceiver

    abstract suspend fun execute(toxCore: ToxCore): Result

}