package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.toxoid.clean.data.repository.OwnToxAddressRepository
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import javax.inject.Inject

class GetOwnToxAddressUseCase @Inject constructor(
    private val ownToxAddressRepository: OwnToxAddressRepository,
) {

    suspend fun execute(): ToxAddress {
        return ownToxAddressRepository.getOwnToxAddress()
    }

}