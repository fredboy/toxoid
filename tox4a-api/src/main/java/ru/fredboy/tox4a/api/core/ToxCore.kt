package ru.fredboy.tox4a.api.core

import ru.fredboy.tox4a.api.core.callbacks.ToxCoreEventListener
import ru.fredboy.tox4a.api.core.data.*
import ru.fredboy.tox4a.api.core.data.enums.ToxFileControl
import ru.fredboy.tox4a.api.core.data.enums.ToxFileKind
import ru.fredboy.tox4a.api.core.data.enums.ToxMessageType
import ru.fredboy.tox4a.api.core.data.enums.ToxUserStatus
import ru.fredboy.tox4a.api.core.options.ToxOptions
import java.io.Closeable

interface ToxCore : Closeable {

    fun getSaveData(): ByteArray

    fun load(toxOptions: ToxOptions): ToxCore

    fun bootstrap(address: String, port: UShort, publicKey: ToxPublicKey)

    fun getUdpPort(): UShort

    fun getTcpPort(): UShort

    fun getDhtId(): ToxPublicKey

    fun getIterationInterval(): Int

    fun iterate(toxCoreEventListener: ToxCoreEventListener)

    fun getPublicKey(): ToxPublicKey

    fun getSecretKey(): ToxSecretKey

    fun setNospam(nospam: Int)

    fun getNospam(): Int

    fun getAddress(): ToxFriendAddress

    fun setName(name: ToxNickname)

    fun getName(): ToxNickname

    fun setStatusMessage(message: ToxStatusMessage)

    fun getStatusMessage(): ToxStatusMessage

    fun setStatus(status: ToxUserStatus)

    fun getStatus(): ToxUserStatus

    fun addFriend(address: ToxFriendAddress, message: ToxFriendRequestMessage): ToxFriendNumber

    fun addFriendNoRequest(publicKey: ToxPublicKey): ToxFriendNumber

    fun deleteFriend(friendNumber: ToxFriendNumber)

    fun friendByPublicKey(publicKey: ToxPublicKey): ToxFriendNumber

    fun getFriendPublicKey(friendNumber: ToxFriendNumber): ToxPublicKey

    fun friendExists(friendNumber: ToxFriendNumber): Boolean

    fun getFriendList(): List<ToxFriendNumber>

    fun setTyping(friendNumber: ToxFriendNumber, typing: Boolean)

    fun friendSendMessage(friendNumber: ToxFriendNumber, messageType: ToxMessageType, timeDelta: Int, message: ToxFriendMessage): Int

    fun fileControl(friendNumber: ToxFriendNumber, fileNumber: Int, control: ToxFileControl)

    fun fileSeek(friendNumber: ToxFriendNumber, fileNumber: Int, position: Long)

    fun getFileFileId(friendNumber: ToxFriendNumber, fileNumber: Int): ToxFileId

    fun fileSend(friendNumber: ToxFriendNumber, kind: ToxFileKind, fileSize: Long, fileId: ToxFileId, fileName: ToxFileName): Int

    fun fileSendChunk(friendNumber: ToxFriendNumber, fileNumber: Int, position: Long, data: ByteArray)

    fun friendSendLossyPacket(friendNumber: ToxFriendNumber, data: ToxLossyPacket)

    fun friendSendLosslessPacket(friendNumber: ToxFriendNumber, data: ToxLosslessPacket)


}