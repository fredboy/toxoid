@file:OptIn(DelicateCoroutinesApi::class, ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.callbacks.ToxCoreEventListener
import ru.fredboy.tox4a.api.core.data.ToxFileId
import ru.fredboy.tox4a.api.core.data.ToxFileName
import ru.fredboy.tox4a.api.core.data.ToxFriendAddress
import ru.fredboy.tox4a.api.core.data.ToxFriendMessage
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.ToxFriendRequestMessage
import ru.fredboy.tox4a.api.core.data.ToxLosslessPacket
import ru.fredboy.tox4a.api.core.data.ToxLossyPacket
import ru.fredboy.tox4a.api.core.data.ToxNickname
import ru.fredboy.tox4a.api.core.data.ToxPublicKey
import ru.fredboy.tox4a.api.core.data.ToxSecretKey
import ru.fredboy.tox4a.api.core.data.ToxStatusMessage
import ru.fredboy.tox4a.api.core.data.enums.ToxFileControl
import ru.fredboy.tox4a.api.core.data.enums.ToxFileKind
import ru.fredboy.tox4a.api.core.data.enums.ToxMessageType
import ru.fredboy.tox4a.api.core.data.enums.ToxUserStatus
import ru.fredboy.tox4a.api.core.options.ToxOptions
import ru.fredboy.toxoid.clean.domain.usecase.tox.CreateNewToxOptionsUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.LoadToxDataUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.GetCurrentUserUseCase
import splitties.coroutines.SuspendLazy
import splitties.coroutines.suspendLazy
import splitties.experimental.ExperimentalSplittiesApi

@Module
@InstallIn(ServiceComponent::class)
class ToxServiceProvidersModule {

    @Provides
    @ServiceScoped
    fun provideToxOptions(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        loadToxDataUseCase: LoadToxDataUseCase,
        createNewToxOptionsUseCase: CreateNewToxOptionsUseCase,
    ): SuspendLazy<ToxOptions> {
        return GlobalScope.suspendLazy {
            getCurrentUserUseCase.execute()?.let { currentUser ->
                loadToxDataUseCase.execute(currentUser.id)
            } ?: createNewToxOptionsUseCase.execute()
        }
    }

    @Provides
    @ServiceScoped
    fun provideToxCore(
        toxOptions: SuspendLazy<@JvmSuppressWildcards ToxOptions>
    ): SuspendLazy<ToxCore> {
        return GlobalScope.suspendLazy {
            object : ToxCore {
                override fun getSaveData(): ByteArray {
                    TODO("Not yet implemented")
                }

                override fun load(toxOptions: ToxOptions): ToxCore {
                    TODO("Not yet implemented")
                }

                override fun bootstrap(address: String, port: UShort, publicKey: ToxPublicKey) {
                    TODO("Not yet implemented")
                }

                override fun getUdpPort(): UShort {
                    TODO("Not yet implemented")
                }

                override fun getTcpPort(): UShort {
                    TODO("Not yet implemented")
                }

                override fun getDhtId(): ToxPublicKey {
                    TODO("Not yet implemented")
                }

                override fun getIterationInterval(): Int {
                    TODO("Not yet implemented")
                }

                override fun iterate(toxCoreEventListener: ToxCoreEventListener) {
                    TODO("Not yet implemented")
                }

                override fun getPublicKey(): ToxPublicKey {
                    TODO("Not yet implemented")
                }

                override fun getSecretKey(): ToxSecretKey {
                    TODO("Not yet implemented")
                }

                override fun setNospam(nospam: Int) {
                    TODO("Not yet implemented")
                }

                override fun getNospam(): Int {
                    TODO("Not yet implemented")
                }

                override fun getAddress(): ToxFriendAddress {
                    TODO("Not yet implemented")
                }

                override fun setName(name: ToxNickname) {
                    TODO("Not yet implemented")
                }

                override fun getName(): ToxNickname {
                    TODO("Not yet implemented")
                }

                override fun setStatusMessage(message: ToxStatusMessage) {
                    TODO("Not yet implemented")
                }

                override fun getStatusMessage(): ToxStatusMessage {
                    TODO("Not yet implemented")
                }

                override fun setStatus(status: ToxUserStatus) {
                    TODO("Not yet implemented")
                }

                override fun getStatus(): ToxUserStatus {
                    TODO("Not yet implemented")
                }

                override fun addFriend(
                    address: ToxFriendAddress,
                    message: ToxFriendRequestMessage
                ): ToxFriendNumber {
                    TODO("Not yet implemented")
                }

                override fun addFriendNoRequest(publicKey: ToxPublicKey): ToxFriendNumber {
                    TODO("Not yet implemented")
                }

                override fun deleteFriend(friendNumber: ToxFriendNumber) {
                    TODO("Not yet implemented")
                }

                override fun friendByPublicKey(publicKey: ToxPublicKey): ToxFriendNumber {
                    TODO("Not yet implemented")
                }

                override fun getFriendPublicKey(friendNumber: ToxFriendNumber): ToxPublicKey {
                    TODO("Not yet implemented")
                }

                override fun friendExists(friendNumber: ToxFriendNumber): Boolean {
                    TODO("Not yet implemented")
                }

                override fun getFriendList(): List<ToxFriendNumber> {
                    TODO("Not yet implemented")
                }

                override fun setTyping(friendNumber: ToxFriendNumber, typing: Boolean) {
                    TODO("Not yet implemented")
                }

                override fun friendSendMessage(
                    friendNumber: ToxFriendNumber,
                    messageType: ToxMessageType,
                    timeDelta: Int,
                    message: ToxFriendMessage
                ): Int {
                    TODO("Not yet implemented")
                }

                override fun fileControl(
                    friendNumber: ToxFriendNumber,
                    fileNumber: Int,
                    control: ToxFileControl
                ) {
                    TODO("Not yet implemented")
                }

                override fun fileSeek(
                    friendNumber: ToxFriendNumber,
                    fileNumber: Int,
                    position: Long
                ) {
                    TODO("Not yet implemented")
                }

                override fun getFileFileId(
                    friendNumber: ToxFriendNumber,
                    fileNumber: Int
                ): ToxFileId {
                    TODO("Not yet implemented")
                }

                override fun fileSend(
                    friendNumber: ToxFriendNumber,
                    kind: ToxFileKind,
                    fileSize: Long,
                    fileId: ToxFileId,
                    fileName: ToxFileName
                ): Int {
                    TODO("Not yet implemented")
                }

                override fun fileSendChunk(
                    friendNumber: ToxFriendNumber,
                    fileNumber: Int,
                    position: Long,
                    data: ByteArray
                ) {
                    TODO("Not yet implemented")
                }

                override fun friendSendLossyPacket(
                    friendNumber: ToxFriendNumber,
                    data: ToxLossyPacket
                ) {
                    TODO("Not yet implemented")
                }

                override fun friendSendLosslessPacket(
                    friendNumber: ToxFriendNumber,
                    data: ToxLosslessPacket
                ) {
                    TODO("Not yet implemented")
                }

                override fun close() {
                    TODO("Not yet implemented")
                }

            }
        }
    }

}