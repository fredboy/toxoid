package ru.fredboy.toxoid.tox

import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.options.ToxOptions
import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.model.tox.FriendRequestData
import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.GetSavedBootstrapNodesUseCase
import ru.fredboy.toxoid.clean.domain.usecase.contact.FlowNewContactNameUseCase
import ru.fredboy.toxoid.clean.domain.usecase.contact.GetContactUpdatesFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.contact.UpdateContactUseCase
import ru.fredboy.toxoid.clean.domain.usecase.friendrequest.BroadcastNewFriendRequestUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.*
import ru.fredboy.toxoid.clean.domain.usecase.user.GetCurrentUserUseCase
import javax.inject.Inject

class ToxServiceUseCases @Inject constructor(
    private val createNewToxOptionsUseCase: CreateNewToxOptionsUseCase,
    private val broadcastNewFriendRequestUseCase: BroadcastNewFriendRequestUseCase,
    private val streamSelfConnectionStatusUseCase: StreamSelfConnectionStatusUseCase,
    private val getSelfConnectionStatusFlowUseCase: GetSelfConnectionStatusFlowUseCase,
    private val getSavedBootstrapNodesUseCase: GetSavedBootstrapNodesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val loadToxDataUseCase: LoadToxDataUseCase,
    private val saveToxDataUseCase: SaveToxDataUseCase,
    private val getLatestSelfConnectionStatusUseCase: GetLatestSelfConnectionStatusUseCase,
    private val flowNewContactNameUseCase: FlowNewContactNameUseCase,
    private val getContactUpdatesFlowUseCase: GetContactUpdatesFlowUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
) {

    suspend fun createNewToxOptions(): ToxOptions {
        return createNewToxOptionsUseCase.execute()
    }

    fun broadcastNewFriendRequest(requestData: FriendRequestData) {
        broadcastNewFriendRequestUseCase.execute(requestData)
    }

    fun streamSelfConnectionStatus(connection: ToxConnection) {
        streamSelfConnectionStatusUseCase.execute(connection)
    }

    fun getSelfConnectionStatusFlow(): Flow<ToxConnection> {
        return getSelfConnectionStatusFlowUseCase.execute()
    }

    suspend fun getSavedBootstrapNodes(): List<BootstrapNode> {
        return getSavedBootstrapNodesUseCase.execute()
    }

    suspend fun getCurrentUser(): LocalUser? {
        return getCurrentUserUseCase.execute()
    }

    suspend fun loadToxData(toxId: String): ToxOptions {
        return loadToxDataUseCase.execute(toxId)
    }

    suspend fun saveToxData(toxId: String, data: ByteArray) {
        saveToxDataUseCase.execute(toxId, data)
    }

    fun getLatestSelfConnectionStatus(): ToxConnection {
        return getLatestSelfConnectionStatusUseCase.execute()
    }

    fun flowNewContactName(friendNameData: NewFriendNameData) {
        flowNewContactNameUseCase.execute(friendNameData)
    }

    fun getContactUpdatesFlow(): Flow<Contact> {
        return getContactUpdatesFlowUseCase.execute()
    }

    suspend fun updateContact(contact: Contact) {
        updateContactUseCase.execute(contact)
    }

}