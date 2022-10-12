package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.OwnToxIdRepository
import ru.fredboy.toxoid.utils.ToxId
import javax.inject.Inject

class SetOwnToxIdUseCase @Inject constructor(
    private val ownOwnToxIdRepository: OwnToxIdRepository
) {

    fun execute(toxId: ToxId) {
        ownOwnToxIdRepository.setOwnToxId(toxId)
    }

}