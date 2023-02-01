package ru.fredboy.toxoid.clean.domain.usecase.bootstrap

import ru.fredboy.toxoid.clean.data.repository.CommonPreferencesRepository
import javax.inject.Inject

class IsFirstLaunchUseCase @Inject constructor(
    private val commonPreferencesRepository: CommonPreferencesRepository,
) {

    fun execute(): Boolean {
        return commonPreferencesRepository.isFirstLaunch()
    }

}