package ru.fredboy.toxoid.clean.presentation.view.newuser

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.presentation.activity.MainActivity
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentNewUserBinding
import ru.fredboy.toxoid.tox.ToxService
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

    private val resultIntent = Intent()

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

        val toxServiceIntent = Intent(activity?.applicationContext, ToxService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(toxServiceIntent)
        } else {
            activity?.startService(toxServiceIntent)
        }

        return binding.root
    }

    override fun setNewUser(user: LocalUser) {
        resultIntent.putExtra(MainActivity.KEY_LOCAL_USER, user)
        activity?.setResult(MainActivity.RESULT_USER_CREATED, resultIntent)
        activity?.finish()
    }

    override fun showUserPhoto(drawable: Drawable) {
        with(binding) {
            newUserProgress.gone()
            newUserPhoto.setImageDrawable(drawable)
        }
    }
}