package ru.fredboy.toxoid.clean.domain.usecase

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.fredboy.toxoid.clean.data.repository.*
import ru.fredboy.toxoid.clean.domain.model.Chat
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.utils.generateRandomStringId
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class InitializeWithMockDataUseCase @Inject constructor(
    private val chatsRepository: ChatsRepository,
    private val localUsersRepository: LocalUsersRepository,
    private val messagesRepository: MessagesRepository,
    private val contactsRepository: ContactsRepository,
    private val commonPreferencesRepository: CommonPreferencesRepository
) {

    fun execute() {
        val currentTimestamp = System.currentTimeMillis()
        val user = LocalUser(id = generateRandomStringId(), name = "User Name")
        val contacts = List(10) { index -> Contact(id = "contact$index", name = "Contact $index") }
        val messages = List(100) { index ->
            Message(
                id = "message$index",
                chatId = "chat${index / 10}",
                senderId = if (index % 2 == 0) user.id else contacts[index / 10].id,
                text = "Wow! Such message! So cool! Unbelievable!!! Number $index",
                date = Date(currentTimestamp - Random.nextLong(1000000000))
            )
        }.sortedBy { it.date }
        val chats = List(10) { index ->
            Chat(
                id = "chat$index",
                peer = contacts[index],
                messages = messages.filter { it.chatId == "chat$index" }
            )
        }

        runBlocking {
            launch {
                localUsersRepository.add(user)
                localUsersRepository.setCurrentId(user.id)

                contactsRepository.add(Contact(user.id, user.name))
                contacts.forEach { contactsRepository.add(it) }
                chats.forEach { chatsRepository.add(it) }
                messages.forEach { messagesRepository.add(it) }
            }
        }
    }

    fun isFirstLaunch(): Boolean {
        return commonPreferencesRepository.isFirstLaunch()
    }

    fun setFirstLaunch() {
        commonPreferencesRepository.setFirstLaunch()
    }

}