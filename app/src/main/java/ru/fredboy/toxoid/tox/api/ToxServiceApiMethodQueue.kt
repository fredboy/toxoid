package ru.fredboy.toxoid.tox.api

import androidx.annotation.WorkerThread
import ru.fredboy.toxoid.tox.api.methods.ToxServiceGenericApiMethod
import java.util.concurrent.LinkedBlockingQueue

class ToxServiceApiMethodQueue {

    private val queue = LinkedBlockingQueue<ToxServiceGenericApiMethod>()

    @WorkerThread
    fun add(method: ToxServiceGenericApiMethod) {
        queue.add(method)
    }

    @WorkerThread
    fun take(): ToxServiceGenericApiMethod {
        return queue.take()
    }

}