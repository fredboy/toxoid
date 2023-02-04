package ru.fredboy.toxoid.clean.presentation.view.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.model.ChatListItemVo
import ru.fredboy.toxoid.databinding.ItemChatListBinding

class ChatListItem(
    private val model: ChatListItemVo
) : AbstractBindingItem<ItemChatListBinding>() {

    override val type = R.id.item_chat_list

    val chatId: String
        get() = model.chatId

    override fun bindView(binding: ItemChatListBinding, payloads: List<Any>) {
        binding.chatListItemPeerName.text = model.peerName
        binding.chatListItemLastMessage.text = model.lastMessageText
        binding.chatListItemLastMessageTime.text = model.lastMessageDateAndTime
        binding.chatListItemPeerPhoto.setImageDrawable(model.contactDrawable)
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemChatListBinding {
        return ItemChatListBinding.inflate(inflater, parent, false)
    }

}