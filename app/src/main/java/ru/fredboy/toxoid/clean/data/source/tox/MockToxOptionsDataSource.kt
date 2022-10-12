package ru.fredboy.toxoid.clean.data.source.tox

import android.content.Context
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.core.enums.ToxProxyType
import im.tox.tox4j.core.enums.ToxSavedataType
import im.tox.tox4j.core.options.ProxyOptions
import im.tox.tox4j.core.options.SaveDataOptions
import im.tox.tox4j.core.options.ToxOptions
import java.io.*
import javax.inject.Inject

class MockToxOptionsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : ToxOptionsDataSource {

    @WorkerThread
    override fun createNew(): ToxOptions {
        return createOptions(null)
    }

    @WorkerThread
    override fun getForUser(userId: String): ToxOptions? {
        return try {
            val cacheDir = context.cacheDir
            val file = File("$cacheDir/$userId.dat")
            val fin = FileInputStream(file)
            val data = fin.readBytes()
            fin.close()
            createOptions(data)
        } catch (e: FileNotFoundException) {
            null
        }
    }

    @WorkerThread
    override fun saveToxData(toxId: String, data: ByteArray) {
        val cacheDir = context.cacheDir
        val file = File("$cacheDir/$toxId.dat")
        val fout = ObjectOutputStream(FileOutputStream(file))
        fout.write(data)
        fout.flush()
        fout.close()
    }

    private fun createOptions(savedData: ByteArray?): ToxOptions {
        return ToxOptions(
            false,
            true,
            true,
            ProxyOptions.`None$`.`MODULE$`,
            ToxCoreConstants.DefaultStartPort(),
            ToxCoreConstants.DefaultEndPort(),
            ToxCoreConstants.DefaultTcpPort(),
            SaveDataOptions.`None$`.`MODULE$`,
            false
        )
    }

}