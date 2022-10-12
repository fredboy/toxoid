package ru.fredboy.toxoid.clean.presentation.view.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.clean.presentation.view.chatlist.ChatListFragmentDirections
import ru.fredboy.toxoid.databinding.FragmentWelcomeBinding
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class WelcomeFragment : BaseMvpFragment(), WelcomeView {

    private lateinit var binding: FragmentWelcomeBinding

    @Inject
    lateinit var presenterProvider: Provider<WelcomePresenter>

    private val presenter: WelcomePresenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater)

        with(binding) {
            welcomeContinueButton.setOnClickListener {
//                presenter.onContinueClick()
                navigateNewUser()
            }
        }

        return binding.root
    }

    override fun finishActivity() {
        activity?.finish()
    }

    private fun navigateNewUser() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToBootstrapFragment()
        findNavController().navigate(action)
    }

}