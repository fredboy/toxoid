package ru.fredboy.toxoid.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> withIoDispatcher(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}