package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentBootstrapBinding
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class BootstrapFragment : BaseMvpFragment(), BootstrapView {

    private lateinit var binding: FragmentBootstrapBinding

    @Inject
    lateinit var presenterProvider: Provider<BootstrapPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBootstrapBinding.inflate(inflater)
        return binding.root
    }

}