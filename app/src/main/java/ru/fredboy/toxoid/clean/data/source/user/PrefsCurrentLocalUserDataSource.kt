package ru.fredboy.toxoid.clean.data.source.user

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsCurrentLocalUserDataSource @Inject constructor(
    @ApplicationContext context: Context
) : CurrentLocalUserDataSource {

    private val lock = Any()

    private val sharedPreferences = context.getSharedPreferences(USERS_PREFS, Context.MODE_PRIVATE)

    override suspend fun getCurrentLocalUserId(): String? {
        synchronized(lock) {
            return sharedPreferences.getString(PREFERRED_USER_ID, null)
        }
    }

    override suspend fun setCurrentLocalUserId(userId: String) {
        synchronized(lock) {
            sharedPreferences.edit(commit = true) {
                putString(PREFERRED_USER_ID, userId)
            }
        }
    }

    companion object {
        private const val USERS_PREFS = "USERS"
        private const val PREFERRED_USER_ID = "PREFERRED_USER_ID"
    }
}