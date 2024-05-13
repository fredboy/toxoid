package ru.fredboy.toxoid.clean.presentation.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.presentation.model.MessageVo
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentChatBinding
import ru.fredboy.toxoid.utils.scrollToBottom
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : BaseMvpFragment(), ChatView {

    @Inject
    lateinit var presenterFactory: ChatPresenter.Factory

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = requireNotNull(_binding)

    private val presenter: ChatPresenter by moxyPresenter { presenterFactory.get(args) }

    private val args by navArgs<ChatFragmentArgs>()

    private val itemAdapter by lazy { ItemAdapter<ChatMessageItem>() }
    private val fastAdapter by lazy { FastAdapter.with(itemAdapter) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater)

        with(binding) {
            chatMessagesRecycler.adapter = fastAdapter
            chatMessagesRecycler
                .addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                    if (bottom < oldBottom) {
                        chatMessagesRecycler.post(::scrollToBottom)
                    }
                }
            chatMessagesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy < 0) {
                        hideSoftInput(binding)
                    }
                }
            })

            chatMessageEdit.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideSoftInput(binding)
                }
            }

            chatSendMessageButton.setOnClickListener { onSendButtonClick() }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun addMessages(messages: List<MessageVo>) {
        itemAdapter.add(messages.map(::ChatMessageItem))
    }

    override fun scrollToBottom() {
        binding.chatMessagesRecycler.scrollToBottom()
    }

    private fun onSendButtonClick() {
        presenter.onSendButtonClicked(binding.chatMessageEdit.text.toString())
        binding.chatMessageEdit.text.clear()
    }

}