package ru.fredboy.toxoid.di.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.fredboy.toxoid.clean.data.source.bootstrap.BootstrapNodeDataSource
import ru.fredboy.toxoid.clean.data.source.retrofit.RetrofitConfig
import ru.fredboy.toxoid.clean.data.source.bootstrap.RetrofitToxChatNodesDataSource
import ru.fredboy.toxoid.clean.data.source.bootstrap.RoomBootstrapNodeDataSource
import ru.fredboy.toxoid.clean.data.source.bootstrap.ToxChatNodesDataSource
import ru.fredboy.toxoid.clean.data.source.chat.ChatDataSource
import ru.fredboy.toxoid.clean.data.source.chat.RoomChatDataSource
import ru.fredboy.toxoid.clean.data.source.contact.ContactDataSource
import ru.fredboy.toxoid.clean.data.source.contact.RoomContactDataSource
import ru.fredboy.toxoid.clean.data.source.message.MessageDataSource
import ru.fredboy.toxoid.clean.data.source.message.RoomMessageDataSource
import ru.fredboy.toxoid.clean.data.source.retrofit.NodesToxChatApi
import ru.fredboy.toxoid.clean.data.source.retrofit.NodesToxChatRetrofitConfig
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import ru.fredboy.toxoid.clean.data.source.tox.CachedFriendRequestDataSource
import ru.fredboy.toxoid.clean.data.source.tox.MockToxOptionsDataSource
import ru.fredboy.toxoid.clean.data.source.tox.RoomCachedFriendRequestDataSource
import ru.fredboy.toxoid.clean.data.source.tox.ToxOptionsDataSource
import ru.fredboy.toxoid.clean.data.source.user.CurrentLocalUserDataSource
import ru.fredboy.toxoid.clean.data.source.user.LocalUserDataSource
import ru.fredboy.toxoid.clean.data.source.user.PrefsCurrentLocalUserDataSource
import ru.fredboy.toxoid.clean.data.source.user.RoomLocalUserDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingsModule {

    @Binds
    abstract fun bindRetrofitConfig(implementation: NodesToxChatRetrofitConfig): RetrofitConfig

    @Binds
    abstract fun bindChatDataSource(implementation: RoomChatDataSource): ChatDataSource

    @Binds
    abstract fun bindMessageDataSource(implementation: RoomMessageDataSource): MessageDataSource

    @Binds
    abstract fun bindContactDataSource(implementation: RoomContactDataSource): ContactDataSource

    @Binds
    abstract fun bindLocalUserDataSource(
        implementation: RoomLocalUserDataSource
    ): LocalUserDataSource

    @Binds
    abstract fun bindCurrentLocalUserDataSource(
        implementation: PrefsCurrentLocalUserDataSource
    ): CurrentLocalUserDataSource

    @Binds
    abstract fun bindToxOptionsDataSource(
        implementation: MockToxOptionsDataSource
    ): ToxOptionsDataSource

    @Binds
    abstract fun bindCachedFriendRequestDataSource(
        implementation: RoomCachedFriendRequestDataSource
    ) : CachedFriendRequestDataSource

    @Binds
    abstract fun bindBootstrapNodeDataSource(
        implementation: RoomBootstrapNodeDataSource
    ) : BootstrapNodeDataSource

    @Binds
    abstract fun bindToxChatNodesDataSource(
        implementation: RetrofitToxChatNodesDataSource
    ) : ToxChatNodesDataSource

}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvidersModule {

    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            MainDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(retrofitConfig: RetrofitConfig): Retrofit {
        return Retrofit.Builder()
            .baseUrl(retrofitConfig.baseUrl)
            .addConverterFactory(retrofitConfig.converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideNodesToxChatApi(retrofit: Retrofit): NodesToxChatApi {
        return retrofit.create()
    }
}