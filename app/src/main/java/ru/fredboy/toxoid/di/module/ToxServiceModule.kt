@file:OptIn(DelicateCoroutinesApi::class, ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.options.ToxOptions
import im.tox.tox4j.impl.jni.ToxCoreImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
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
            ToxCoreImpl(toxOptions())
        }
    }

}