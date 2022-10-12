package ru.fredboy.toxoid.clean.presentation.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.model.MessageVo
import ru.fredboy.toxoid.databinding.ItemChatMessageBinding
import ru.fredboy.toxoid.utils.gone
import ru.fredboy.toxoid.utils.visible

class ChatMessageItem(
    private val model: MessageVo
) : AbstractBindingItem<ItemChatMessageBinding>() {

    override val type = R.id.item_chat_message

    override fun bindView(binding: ItemChatMessageBinding, payloads: List<Any>) {
        with(binding) {
            chatMessage.text = model.text
            chatMessageTime.text = model.date

            if (model.isFromUser) {
                senderMarginView.visible()
            } else {
                senderMarginView.gone()
            }
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemChatMessageBinding {
        return ItemChatMessageBinding.inflate(inflater, parent, false)
    }

}