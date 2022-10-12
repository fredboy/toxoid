package ru.fredboy.toxoid.clean.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.fredboy.toxoid.clean.data.model.room.*
import ru.fredboy.toxoid.clean.data.source.room.dao.*
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        ChatEntity::class,
        MessageEntity::class,
        ContactEntity::class,
        LocalUserEntity::class,
        FriendRequestEntity::class
    ],
    version = MainDatabase.VERSION
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao
    abstract fun chatDao(): ChatDao
    abstract fun localUserDao(): LocalUserDao
    abstract fun friendRequestDao(): FriendRequestDao

    val contactDao get() = contactDao()
    val messageDao get() = messageDao()
    val chatDao get() = chatDao()
    val localUserDao get() = localUserDao()
    val friendRequestDao get() = friendRequestDao()

    companion object {
        const val VERSION = 1

        const val DATABASE_NAME = "toxoid_main_db"
        const val CHAT_TABLE = "chat"
        const val CONTACT_TABLE = "contact"
        const val MESSAGE_TABLE = "message"
        const val LOCAL_USER_TABLE = "local_user"
        const val FRIEND_REQUEST_TABLE = "friend_request"
    }
}