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
import ru.fredboy.tox4a.impl.jni.ToxCoreImpl
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
        return GlobalScope.suspendLazy { ToxCoreImpl() }
    }

}