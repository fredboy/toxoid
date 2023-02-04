package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.toxoid.clean.data.mapper.ToxAddressMapper
import ru.fredboy.toxoid.clean.data.mapper.ToxPublicKeyMapper
import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.utils.validateToxId
import ru.fredboy.toxoid.utils.validateToxPublicKeyString
import javax.inject.Inject

class CreateIdenticonUseCase @Inject constructor(
    private val toxAddressMapper: ToxAddressMapper,
    private val toxPublicKeyMapper: ToxPublicKeyMapper,
) {

    fun execute(toxId: String): Identicon {
        val publicKey = when {
            validateToxId(toxId) -> toxAddressMapper.map(toxId).toToxPublicKey()
            validateToxPublicKeyString(toxId) -> toxPublicKeyMapper.map(toxId)
            else -> throw IllegalArgumentException("toxId is not valid")
        }
        return Identicon(publicKey)
    }

}