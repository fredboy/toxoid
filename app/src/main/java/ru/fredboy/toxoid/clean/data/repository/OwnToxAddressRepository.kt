package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import javax.inject.Inject

class OwnToxAddressRepository @Inject constructor(
    private val toxServiceIntentApi: ToxServiceIntentApi,
) {

    suspend fun getOwnToxAddress(): ToxAddress {
        return withContext(Dispatchers.Main) {
            toxServiceIntentApi.getOwnAddress()
        }
    }

}