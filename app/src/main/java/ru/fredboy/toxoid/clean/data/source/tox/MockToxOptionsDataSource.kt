package ru.fredboy.toxoid.clean.data.source.tox

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.fredboy.tox4a.api.core.options.SaveDataOptions
import ru.fredboy.tox4a.api.core.options.ToxOptions
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("BlockingMethodInNonBlockingContext")
@Singleton
class MockToxOptionsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : ToxOptionsDataSource {

    override suspend fun createNew(): ToxOptions {
        return createOptions(null)
    }

    override suspend fun getForUser(userId: String): ToxOptions {
        val cacheDir = context.cacheDir
        val file = File("$cacheDir/$userId.dat")
        val fin = FileInputStream(file)
        val data = fin.readBytes()
        fin.close()
        return createOptions(data)
    }

    override suspend fun saveToxData(toxId: String, data: ByteArray) {
        val cacheDir = context.cacheDir
        val file = File("$cacheDir/$toxId.dat")
        val fout = FileOutputStream(file)
        fout.write(data)
        fout.flush()
        fout.close()
    }

    private fun createOptions(savedData: ByteArray?): ToxOptions {
        val saveDataOptions = if (savedData == null) {
            SaveDataOptions.NONE
        } else {
            SaveDataOptions.toxSave(savedData)
        }

        return ToxOptions(
            ipv6Enabled = false,
            saveData = saveDataOptions,
            fatalErrors = false
        )
    }

}