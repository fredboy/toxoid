package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import javax.inject.Inject

class InitToxServiceUseCase @Inject constructor(
    private val toxServiceIntentApi: ToxServiceIntentApi,
) {

    fun execute() {
        toxServiceIntentApi.initService()
    }

}