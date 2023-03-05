package ru.fredboy.tox4a.core

import ru.fredboy.tox4a.core.data.*
import ru.fredboy.tox4a.core.options.Options
import ru.fredboy.tox4a.core.proto.UserStatus
import java.io.Closeable

interface ToxCore : Closeable {

    fun getSaveData(): ByteArray

    fun load(options: Options): ToxCore

    fun bootstrap(address: String, port: UShort, publicKey: ToxPublicKey)

    fun getUdpPort(): UShort

    fun getTcpPort(): UShort

    fun getDhtId(): ToxPublicKey

    fun getIterationInterval(): Int

    fun iterate()

    fun getPublicKey(): ToxPublicKey

    fun getSecretKey(): ToxSecretKey

    fun setNospam(nospam: Int)

    fun getNospam(): Int

    fun getAddress(): ToxFriendAddress

    fun setName(name: ToxNickname)

    fun getName(): ToxNickname

    fun setStatusMessage(message: ToxStatusMessage)

    fun getStatusMessage(): ToxStatusMessage

    fun setStatus(status: UserStatus)

    fun getStatus(): UserStatus


}