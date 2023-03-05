package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.*
import ru.fredboy.tox4a.core.data.enums.*

class CompositeToxCoreEventListener(
    private val fileChunkRequestCallback: FileChunkRequestCallback? = null,
    private val fileRecvCallback: FileRecvCallback? = null,
    private val fileRecvChunkCallback: FileRecvChunkCallback? = null,
    private val fileRecvControlCallback: FileRecvControlCallback? = null,
    private val friendConnectionStatusCallback: FriendConnectionStatusCallback? = null,
    private val friendLosslessPacketCallback: FriendLosslessPacketCallback? = null,
    private val friendLossyPacketCallback: FriendLossyPacketCallback? = null,
    private val friendMessageCallback: FriendMessageCallback? = null,
    private val friendNameCallback: FriendNameCallback? = null,
    private val friendReadReceiptCallback: FriendReadReceiptCallback? = null,
    private val friendRequestCallback: FriendRequestCallback? = null,
    private val friendStatusCallback: FriendStatusCallback? = null,
    private val friendStatusMessageCallback: FriendStatusMessageCallback? = null,
    private val friendTypingCallback: FriendTypingCallback? = null,
    private val selfConnectionStatusCallback: SelfConnectionStatusCallback? = null,
) : ToxCoreEventListener {

    override fun fileChunkRequest(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        length: Int
    ) {
        fileChunkRequestCallback?.fileChunkRequest(
            toxFriendNumber = toxFriendNumber,
            fileNumber = fileNumber,
            position = position,
            length = length
        )
    }

    override fun fileRecv(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        kind: ToxFileKind,
        fileSize: Long,
        filename: ToxFileName
    ) {
        fileRecvCallback?.fileRecv(
            toxFriendNumber = toxFriendNumber,
            fileNumber = fileNumber,
            kind = kind,
            fileSize = fileSize,
            filename = filename
        )
    }

    override fun fileRecvChunk(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        data: ByteArray
    ) {
        fileRecvChunkCallback?.fileRecvChunk(
            toxFriendNumber = toxFriendNumber,
            fileNumber = fileNumber,
            position = position,
            data = data
        )
    }

    override fun fileRecvControl(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        control: ToxFileControl
    ) {
        fileRecvControlCallback?.fileRecvControl(
            toxFriendNumber = toxFriendNumber,
            fileNumber = fileNumber,
            control = control
        )
    }

    override fun friendConnectionStatus(
        toxFriendNumber: ToxFriendNumber,
        connectionStatus: ToxConnection
    ) {
        friendConnectionStatusCallback?.friendConnectionStatus(
            toxFriendNumber = toxFriendNumber,
            connectionStatus = connectionStatus
        )
    }

    override fun friendLosslessPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLosslessPacket
    ) {
        friendLosslessPacketCallback?.friendLosslessPacket(
            toxFriendNumber = toxFriendNumber,
            data = data
        )
    }

    override fun friendLossyPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLossyPacket
    ) {
        friendLossyPacketCallback?.friendLossyPacket(
            toxFriendNumber = toxFriendNumber,
            data = data
        )
    }

    override fun friendMessage(
        toxFriendNumber: ToxFriendNumber,
        messageType: ToxMessageType,
        timeDelta: Int,
        message: ToxFriendMessage
    ) {
        friendMessageCallback?.friendMessage(
            toxFriendNumber = toxFriendNumber,
            messageType = messageType,
            timeDelta = timeDelta,
            message = message
        )
    }

    override fun friendName(
        toxFriendNumber: ToxFriendNumber,
        name: ToxNickname
    ) {
        friendNameCallback?.friendName(
            toxFriendNumber = toxFriendNumber,
            name = name
        )
    }

    override fun friendReadReceipt(
        toxFriendNumber: ToxFriendNumber,
        messageId: Int
    ) {
        friendReadReceiptCallback?.friendReadReceipt(
            toxFriendNumber = toxFriendNumber,
            messageId = messageId
        )
    }

    override fun friendRequest(
        publicKey: ToxPublicKey,
        timeDelta: Int,
        message: ToxFriendRequestMessage
    ) {
        friendRequestCallback?.friendRequest(
            publicKey = publicKey,
            timeDelta = timeDelta,
            message = message
        )
    }

    override fun friendStatus(
        toxFriendNumber: ToxFriendNumber,
        status: ToxUserStatus
    ) {
        friendStatusCallback?.friendStatus(
            toxFriendNumber = toxFriendNumber,
            status = status
        )
    }

    override fun friendStatusMessage(
        toxFriendNumber: ToxFriendNumber,
        message: ToxStatusMessage
    ) {
        friendStatusMessageCallback?.friendStatusMessage(
            toxFriendNumber = toxFriendNumber,
            message = message
        )
    }

    override fun friendTyping(
        toxFriendNumber: ToxFriendNumber,
        isTyping: Boolean
    ) {
        friendTypingCallback?.friendTyping(
            toxFriendNumber = toxFriendNumber,
            isTyping = isTyping
        )
    }

    override fun selfConnectionStatus(
        connectionStatus: ToxConnection
    ) {
        selfConnectionStatusCallback?.selfConnectionStatus(
            connectionStatus = connectionStatus
        )
    }

}