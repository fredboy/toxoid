package ru.fredboy.toxoid.tox

import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.options.ToxOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.GetSavedBootstrapNodesUseCase
import ru.fredboy.toxoid.clean.domain.usecase.contact.SetContactNameUseCase
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
    private val setContactNameUseCase: SetContactNameUseCase,
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

    fun setContactName(contactId: String, newName: String) {
        // FIXME: !!!
        CoroutineScope(Dispatchers.IO).launch { setContactNameUseCase.execute(contactId, newName) }
    }

}