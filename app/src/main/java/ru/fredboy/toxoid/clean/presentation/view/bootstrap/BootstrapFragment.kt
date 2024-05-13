package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.select.selectExtension
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

    private var _binding: FragmentBootstrapBinding? = null
    private val binding: FragmentBootstrapBinding
        get() = requireNotNull(_binding)

    @Inject
    lateinit var presenterProvider: Provider<BootstrapPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val itemAdapter by lazy { ItemAdapter<BootstrapNodeItem>() }
    private val fastAdapter by lazy { FastAdapter.with(itemAdapter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBootstrapBinding.inflate(inflater)

        with(binding) {
            bootstrapProgress.visible()

            fastAdapter.selectExtension {
                isSelectable = true
                multiSelect = true
                selectOnLongClick = false
            }

            bootstrapNodesRecycler.adapter = fastAdapter

            bootstrapContinue.setOnClickListener {
                presenter.onNodesSelected(getSelectedIndices())
                findNavController().navigate(
                    BootstrapFragmentDirections.actionBootstrapFragmentToNewUserFragment()
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSelectedIndices(): List<Int> {
        return itemAdapter.adapterItems.mapIndexedNotNull { index, bootstrapNodeItem ->
            index.takeIf { bootstrapNodeItem.isSelected }
        }
    }

    override fun setNodes(nodes: List<BootstrapNodeVo>) {
        itemAdapter.add(nodes.map(::BootstrapNodeItem))
        binding.bootstrapProgress.gone()
    }

}