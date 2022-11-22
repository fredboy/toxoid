package ru.fredboy.toxoid.clean.presentation.view.newuser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentNewUserBinding
import ru.fredboy.toxoid.utils.gone
import ru.fredboy.toxoid.utils.visible
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class NewUserFragment : BaseMvpFragment(), NewUserView {

    private lateinit var binding: FragmentNewUserBinding

    @Inject
    lateinit var presenterProvider: Provider<NewUserPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewUserBinding.inflate(inflater)

        with(binding) {
            newUserProgress.visible()

            newUserContinueButton.setOnClickListener {
                presenter.createNewUser(
                    name = newUserNameEdit.text.toString(),
                    password = newUserPasswordEdit.text.toString()
                )
            }
        }

        presenter.initToxService()

        return binding.root
    }

    override fun setNewUser(user: LocalUser) {
        activity?.finish()
    }

    override fun showUserPhoto(drawable: Drawable) {
        with(binding) {
            newUserProgress.gone()
            newUserPhoto.setImageDrawable(drawable)
        }
    }
}