package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.source.preferences.CommonPreferencesDataSource
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class CommonPreferencesRepository @Inject constructor(
    private val commonPreferencesDataSource: CommonPreferencesDataSource
) {

    fun isFirstLaunch(): Boolean {
        return commonPreferencesDataSource.isFirstLaunch()
    }

    fun setFirstLaunch() {
        commonPreferencesDataSource.setFirstLaunch()
    }

}