package ru.fredboy.toxoid.tox

import im.tox.tox4j.core.callbacks.ToxCoreEventListener
import im.tox.tox4j.core.data.ToxFileName
import im.tox.tox4j.core.data.ToxFriendMessage
import im.tox.tox4j.core.data.ToxFriendNumber
import im.tox.tox4j.core.data.ToxFriendRequestMessage
import im.tox.tox4j.core.data.ToxLosslessPacket
import im.tox.tox4j.core.data.ToxLossyPacket
import im.tox.tox4j.core.data.ToxNickname
import im.tox.tox4j.core.data.ToxPublicKey
import im.tox.tox4j.core.data.ToxStatusMessage
import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.enums.ToxFileControl
import im.tox.tox4j.core.enums.ToxMessageType
import im.tox.tox4j.core.enums.ToxUserStatus
import ru.fredboy.toxoid.clean.data.model.tox.FriendRequestData
import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData
import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData

class ToxCoreCallbacks<ToxCoreState>(
    private val toxServiceUseCases: ToxServiceUseCases,
) : ToxCoreEventListener<ToxCoreState> {

    override fun fileChunkRequest(
        friendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        length: Int,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun fileRecv(
        friendNumber: ToxFriendNumber,
        fileNumber: Int,
        kind: Int,
        fileSize: Long,
        filename: ToxFileName,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun fileRecvChunk(
        friendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        data: ByteArray,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun fileRecvControl(
        friendNumber: ToxFriendNumber,
        fileNumber: Int,
        control: ToxFileControl,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendConnectionStatus(
        friendNumber: ToxFriendNumber,
        connectionStatus: ToxConnection,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendLosslessPacket(
        friendNumber: ToxFriendNumber,
        data: ToxLosslessPacket,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendLossyPacket(
        friendNumber: ToxFriendNumber,
        data: ToxLossyPacket,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendMessage(
        friendNumber: ToxFriendNumber,
        messageType: ToxMessageType,
        timeDelta: Int,
        message: ToxFriendMessage,
        state: ToxCoreState
    ): ToxCoreState {
        toxServiceUseCases.flowIncomingMessage(
            incomingMessageData = IncomingMessageData(
                friendNumber = friendNumber.value,
                messageBytes = message.value
            )
        )

        return state
    }

    override fun friendName(
        friendNumber: ToxFriendNumber,
        name: ToxNickname,
        state: ToxCoreState
    ): ToxCoreState {
        toxServiceUseCases.flowNewContactName(
            friendNameData = NewFriendNameData(
                friendNumber = friendNumber.value,
                newFriendNameBytes = name.value
            )
        )

        return state
    }

    override fun friendReadReceipt(
        friendNumber: ToxFriendNumber,
        messageId: Int,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendRequest(
        publicKey: ToxPublicKey,
        timeDelta: Int,
        message: ToxFriendRequestMessage,
        state: ToxCoreState
    ): ToxCoreState {
        toxServiceUseCases.broadcastNewFriendRequest(
            requestData = FriendRequestData(
                publicKeyBytes = publicKey.value,
                messageBytes = message.value
            )
        )

        return state
    }

    override fun friendStatus(
        friendNumber: ToxFriendNumber,
        status: ToxUserStatus,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendStatusMessage(
        friendNumber: ToxFriendNumber,
        message: ToxStatusMessage,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun friendTyping(
        friendNumber: ToxFriendNumber,
        isTyping: Boolean,
        state: ToxCoreState
    ): ToxCoreState {
        return state
    }

    override fun selfConnectionStatus(
        connectionStatus: ToxConnection,
        state: ToxCoreState
    ): ToxCoreState {
        toxServiceUseCases.streamSelfConnectionStatus(connectionStatus)
        return state
    }
}