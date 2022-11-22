@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox.api

import im.tox.tox4j.core.ToxCore
import splitties.coroutines.SuspendLazy
import splitties.experimental.ExperimentalSplittiesApi

class ToxCoreLazy(
    val value: SuspendLazy<ToxCore>
) {
    suspend operator fun invoke() = value()
}