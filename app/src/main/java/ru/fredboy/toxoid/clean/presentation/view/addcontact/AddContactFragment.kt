package ru.fredboy.toxoid.clean.presentation.view.addcontact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpBottomSheetDialogFragment
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.view.qrscan.QrScanFragment
import ru.fredboy.toxoid.databinding.FragmentAddContactBinding
import ru.fredboy.toxoid.utils.getNavigationResult
import ru.fredboy.toxoid.utils.validateToxId
import splitties.views.imageResource
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
            addContactPhoto.imageResource = R.drawable.ic_userpic_placeholder

            getNavigationResult<String>(QrScanFragment.SCAN_RESULT_KEY)
                ?.observe(this@AddContactFragment as LifecycleOwner) {result ->
                    addContactToxidInput.setText(result)
                    findNavController().popBackStack()
                }

            addContactScanQrButton.setOnClickListener {
                AddContactFragmentDirections.actionAddContactFragmentToQrScanFragment()
                    .let { directions -> findNavController().navigate(directions) }
            }

            addContactAddButton.setOnClickListener {
                validateToxIdAndSendRequest(
                    toxId = addContactToxidInput.text.toString()
                )
            }

            addContactToxidInput.doOnTextChanged { text, start, before, count ->
                val toxId = text?.toString()?.takeIf { validateToxId(it) } ?: return@doOnTextChanged
                addContactPhoto.setImageDrawable(presenter.createIdenticonDrawable(toxId))
            }

            addContactToxidInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    return@setOnEditorActionListener validateToxIdAndSendRequest(
                        toxId = addContactToxidInput.text.toString()
                    )
                }
                return@setOnEditorActionListener false
            }
        }

        return binding.root
    }

    private fun validateToxIdAndSendRequest(toxId: String): Boolean {
        return validateToxId(toxId).also { valid ->
            if (valid) {
                presenter.sendFriendRequest(toxId)
                hideSoftInput()
                this.dismiss()
            }
        }
    }

    private fun hideSoftInput() {
        val inputManager = context
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
        inputManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}