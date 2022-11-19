package ru.fredboy.toxoid.clean.presentation.view.addcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpBottomSheetDialogFragment
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.databinding.FragmentAddContactBinding
import ru.fredboy.toxoid.utils.validateToxId
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class AddContactFragment : MvpBottomSheetDialogFragment(), AddContactView {

    private lateinit var binding: FragmentAddContactBinding

    @Inject
    lateinit var presenterProvider: Provider<AddContactPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater)

        with(binding) {
            addContactAddButton.setOnClickListener {
                val toxId = addContactToxidInput.text.toString()
                if (!validateToxId(toxId)) {
                    return@setOnClickListener
                }

                presenter.sendFriendRequest(toxId)
            }
        }

        return binding.root
    }

}