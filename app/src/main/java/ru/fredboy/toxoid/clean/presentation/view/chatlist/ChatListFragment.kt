package ru.fredboy.toxoid.clean.presentation.view.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.dsl.itemAdapter
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.presentation.model.ChatListItemVo
import ru.fredboy.toxoid.clean.presentation.model.MessageVo
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentChatListBinding
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class ChatListFragment : BaseMvpFragment(), ChatListView {

    @Inject
    lateinit var presenterProvider: Provider<ChatListPresenter>

    private lateinit var binding: FragmentChatListBinding

    private val presenter: ChatListPresenter by moxyPresenter { presenterProvider.get() }

    private val itemAdapter by lazy { ModelAdapter(::ChatListItem) }
    private val fastAdapter by lazy { FastAdapter.with(itemAdapter) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater)

        with(binding) {
            chatListRecycler.adapter = fastAdapter

            chatListAddContact.setOnClickListener {
                findNavController().navigate(
                    ChatListFragmentDirections.actionChatListFragmentToAddContactFragment()
                )
            }
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            presenter.onListItemClick(item.chatId)
            false
        }

        return binding.root
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun addChatItems(items: List<ChatListItemVo>) {
        itemAdapter.setItemsWithDiff(items)
    }

    override fun insertItem(chatListItemVo: ChatListItemVo) {
        val index = itemAdapter.adapterItems.indexOfFirst { it.chatId == chatListItemVo.chatId }
        if (index > -1) {
            itemAdapter.remove(index)
        }
        itemAdapter.add(0, chatListItemVo)
    }

    override fun navigateToChat(chatId: String, localUserId: String) {
        val action = ChatListFragmentDirections
            .actionChatListFragmentToChatFragment(chatId, localUserId)
        findNavController().navigate(action)
    }
}