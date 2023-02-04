package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.toxoid.clean.data.mapper.ToxAddressMapper
import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.utils.validateToxId
import javax.inject.Inject

class CreateIdenticonUseCase @Inject constructor(
    private val toxAddressMapper: ToxAddressMapper
) {

    fun execute(toxId: String): Identicon {
        if (!validateToxId(toxId)) {
            throw IllegalArgumentException("toxId is not valid")
        }
        val toxAddress = toxAddressMapper.map(toxId)
        return Identicon(toxAddress.toToxPublicKey())
    }

}