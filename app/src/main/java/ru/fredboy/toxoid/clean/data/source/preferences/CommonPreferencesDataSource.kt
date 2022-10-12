package ru.fredboy.toxoid.clean.data.source.preferences

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) {

    private val lock = Any()

    private val sharedPreferences = context.getSharedPreferences(COMMON_PREFS, Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        synchronized(lock) {
            return sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, true)
        }
    }

    fun setFirstLaunch() {
        synchronized(lock) {
            sharedPreferences.edit(commit = true) {
                putBoolean(FIRST_LAUNCH_KEY, false)
            }
        }
    }

    companion object {
        private const val COMMON_PREFS = "COMMON_PREFS"

        private const val FIRST_LAUNCH_KEY = "FIRST_LAUNCH"
    }

}