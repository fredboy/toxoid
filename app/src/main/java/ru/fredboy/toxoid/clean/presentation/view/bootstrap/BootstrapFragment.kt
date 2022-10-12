package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import ru.fredboy.toxoid.clean.presentation.model.BootstrapNodeVo
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpFragment
import ru.fredboy.toxoid.databinding.FragmentBootstrapBinding
import ru.fredboy.toxoid.utils.gone
import ru.fredboy.toxoid.utils.visible
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class BootstrapFragment : BaseMvpFragment(), BootstrapView {

    private lateinit var binding: FragmentBootstrapBinding

    @Inject
    lateinit var presenterProvider: Provider<BootstrapPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val itemAdapter by lazy { ItemAdapter<BootstrapNodeItem>() }
    private val fastAdapter by lazy { FastAdapter.with(itemAdapter) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBootstrapBinding.inflate(inflater)

        with(binding) {
            bootstrapProgress.visible()
            bootstrapNodesRecycler.adapter = fastAdapter

            bootstrapContinue.setOnClickListener {
                findNavController().navigate(
                    BootstrapFragmentDirections.actionBootstrapFragmentToNewUserFragment())
            }
        }

        return binding.root
    }

    override fun setNodes(nodes: List<BootstrapNodeVo>) {
        itemAdapter.add(nodes.mapIndexed { index, node ->
            BootstrapNodeItem(node) { selected ->
                presenter.onNodeSwitched(index, selected)
            }
        })
        binding.bootstrapProgress.gone()
    }

}