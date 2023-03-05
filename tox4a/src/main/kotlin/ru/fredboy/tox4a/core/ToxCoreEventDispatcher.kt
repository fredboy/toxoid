package ru.fredboy.tox4a.core

import ru.fredboy.tox4a.core.callbacks.*
import ru.fredboy.tox4a.core.data.*
import ru.fredboy.tox4a.core.data.ToxCoreProtoConverter
import ru.fredboy.tox4a.core.proto.*

internal object ToxCoreEventDispatcher {

    private fun dispatchSelfConnectionStatus(
        callback: SelfConnectionStatusCallback,
        selfConnectionStatusList: List<SelfConnectionStatus>,
    ) {
        selfConnectionStatusList.forEach { selfConnectionStatus ->
            callback.selfConnectionStatus(
                connectionStatus = ToxCoreProtoConverter.convert(selfConnectionStatus.connectionStatus),
            )
        }
    }

    private fun dispatchFriendName(
        callback: FriendNameCallback,
        friendNameList: List<FriendName>,
    ) {
        friendNameList.forEach { friendName ->
            callback.friendName(
                toxFriendNumber = ToxFriendNumber(friendName.friendNumber),
                name = ToxNickname(friendName.name.toByteArray()),
            )
        }
    }

    private fun dispatchFriendStatusMessage(
        callback: FriendStatusMessageCallback,
        friendStatusMessageList: List<FriendStatusMessage>,
    ) {
        friendStatusMessageList.forEach { friendStatusMessage ->
            callback.friendStatusMessage(
                toxFriendNumber = ToxFriendNumber(friendStatusMessage.friendNumber),
                message = ToxStatusMessage(friendStatusMessage.message.toByteArray()),
            )
        }
    }

    private fun dispatchFriendStatus(
        callback: FriendStatusCallback,
        friendStatusList: List<FriendStatus>,
    ) {
        friendStatusList.forEach { friendStatus ->
            callback.friendStatus(
                toxFriendNumber = ToxFriendNumber(friendStatus.friendNumber),
                status = ToxCoreProtoConverter.convert(friendStatus.status),
            )
        }
    }

    private fun dispatchFriendConnectionStatus(
        callback: FriendConnectionStatusCallback,
        friendConnectionStatusList: List<FriendConnectionStatus>,
    ) {
        friendConnectionStatusList.forEach { friendConnectionStatus ->
            callback.friendConnectionStatus(
                toxFriendNumber = ToxFriendNumber(friendConnectionStatus.friendNumber),
                connectionStatus = ToxCoreProtoConverter.convert(friendConnectionStatus.connectionStatus),
            )
        }
    }

    private fun dispatchFriendTyping(
        callback: FriendTypingCallback,
        friendTypingList: List<FriendTyping>,
    ) {
        friendTypingList.forEach { friendTyping ->
            callback.friendTyping(
                toxFriendNumber = ToxFriendNumber(friendTyping.friendNumber),
                isTyping = friendTyping.isTyping,
            )
        }
    }

    private fun dispatchFriendReadReceipt(
        callback: FriendReadReceiptCallback,
        friendReadReceiptList: List<FriendReadReceipt>,
    ) {
        friendReadReceiptList.forEach { friendReadReceipt ->
            callback.friendReadReceipt(
                toxFriendNumber = ToxFriendNumber(friendReadReceipt.friendNumber),
                messageId = friendReadReceipt.messageId,
            )
        }
    }

    private fun dispatchFriendRequest(
        callback: FriendRequestCallback,
        friendRequestList: List<FriendRequest>,
    ) {
        friendRequestList.forEach { friendRequest ->
            callback.friendRequest(
                publicKey = ToxPublicKey(friendRequest.publicKey.toByteArray()),
                timeDelta = friendRequest.timeDelta,
                message = ToxFriendRequestMessage(friendRequest.message.toByteArray()),
            )
        }
    }

    private fun dispatchFriendMessage(
        callback: FriendMessageCallback,
        friendMessageList: List<FriendMessage>,
    ) {
        friendMessageList.forEach { friendMessage ->
            callback.friendMessage(
                toxFriendNumber = ToxFriendNumber(friendMessage.friendNumber),
                messageType = ToxCoreProtoConverter.convert(friendMessage.type),
                timeDelta = friendMessage.timeDelta,
                message = ToxFriendMessage(friendMessage.message.toByteArray()),
            )
        }
    }

    private fun dispatchFileRecvControl(
        callback: FileRecvControlCallback,
        fileRecvControlList: List<FileRecvControl>,
    ) {
        fileRecvControlList.forEach { fileRecvControl ->
            callback.fileRecvControl(
                toxFriendNumber = ToxFriendNumber(fileRecvControl.friendNumber),
                fileNumber = fileRecvControl.fileNumber,
                control = ToxCoreProtoConverter.convert(fileRecvControl.control),
            )
        }
    }

    private fun dispatchFileChunkRequest(
        callback: FileChunkRequestCallback,
        fileChunkRequestList: List<FileChunkRequest>,
    ) {
        fileChunkRequestList.forEach { fileChunkRequest ->
            callback.fileChunkRequest(
                toxFriendNumber = ToxFriendNumber(fileChunkRequest.friendNumber),
                fileNumber = fileChunkRequest.fileNumber,
                position = fileChunkRequest.position,
                length = fileChunkRequest.length,
            )
        }
    }

    private fun dispatchFileRecv(
        callback: FileRecvCallback,
        fileRectList: List<FileRecv>,
    ) {
        fileRectList.forEach { fileRecv ->
            callback.fileRecv(
                toxFriendNumber = ToxFriendNumber(fileRecv.friendNumber),
                fileNumber = fileRecv.fileNumber,
                kind = ToxCoreProtoConverter.convert(fileRecv.kind),
                fileSize = fileRecv.fileSize,
                filename = ToxFileName(fileRecv.filename.toByteArray()),
            )
        }
    }

    private fun dispatchFileRecvChunk(
        callback: FileRecvChunkCallback,
        fileRecvChunkList: List<FileRecvChunk>,
    ) {
        fileRecvChunkList.forEach { fileRecvChunk ->
            callback.fileRecvChunk(
                toxFriendNumber = ToxFriendNumber(fileRecvChunk.fileNumber),
                fileNumber = fileRecvChunk.fileNumber,
                position = fileRecvChunk.position,
                data = fileRecvChunk.data.toByteArray(),
            )
        }
    }

    private fun dispatchFriendLossyPacket(
        callback: FriendLossyPacketCallback,
        friendLossyPacketList: List<FriendLossyPacket>,
    ) {
        friendLossyPacketList.forEach { friendLossyPacket ->
            callback.friendLossyPacket(
                toxFriendNumber = ToxFriendNumber(friendLossyPacket.friendNumber),
                data = ToxLossyPacket(friendLossyPacket.data.toByteArray()),
            )
        }
    }

    private fun dispatchFriendLosslessPacket(
        callback: FriendLosslessPacketCallback,
        friendLosslessPacketList: List<FriendLosslessPacket>,
    ) {
        friendLosslessPacketList.forEach { friendLosslessPacket ->
            callback.friendLosslessPacket(
                toxFriendNumber = ToxFriendNumber(friendLosslessPacket.friendNumber),
                data = ToxLosslessPacket(friendLosslessPacket.data.toByteArray()),
            )
        }
    }

    fun dispatch(
        eventListener: ToxCoreEventListener,
        eventData: ByteArray?,
    ) {
        if (eventData == null) {
            return
        }

        val events = CoreEvents.parseFrom(eventData)

        with(events) {
             dispatchSelfConnectionStatus(eventListener, selfConnectionStatusList)
             dispatchFriendName(eventListener, friendNameList)
             dispatchFriendStatusMessage(eventListener, friendStatusMessageList)
             dispatchFriendStatus(eventListener, friendStatusList)
             dispatchFriendConnectionStatus(eventListener, friendConnectionStatusList)
             dispatchFriendTyping(eventListener, friendTypingList)
             dispatchFriendReadReceipt(eventListener, friendReadReceiptList)
             dispatchFriendRequest(eventListener, friendRequestList)
             dispatchFriendMessage(eventListener, friendMessageList)
             dispatchFileRecvControl(eventListener, fileRecvControlList)
             dispatchFileChunkRequest(eventListener, fileChunkRequestList)
             dispatchFileRecv(eventListener, fileRecvList)
             dispatchFileRecvChunk(eventListener, fileRecvChunkList)
             dispatchFriendLossyPacket(eventListener, friendLossyPacketList)
             dispatchFriendLosslessPacket(eventListener, friendLosslessPacketList)
        }
    }

}