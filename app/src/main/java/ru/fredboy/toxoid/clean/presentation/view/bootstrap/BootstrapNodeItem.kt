package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.databinding.ItemBootstrapNodeBinding
import ru.fredboy.toxoid.utils.countryCodeToEmojiFlag

class BootstrapNodeItem(
    private val model: BootstrapNode
) : AbstractBindingItem<ItemBootstrapNodeBinding>() {

    override val type = R.id.item_bootstrap_node

    override fun bindView(binding: ItemBootstrapNodeBinding, payloads: List<Any>) {
        with(binding) {
            bootstrapNodeCountry.text = countryCodeToEmojiFlag(model.location)
            bootstrapNodeIpv4.text = model.ipv4
            bootstrapNodeIpv6.text = model.ipv6

            bootstrapNodeOnlineStatus.setImageResource(
                if (model.status)
                    R.drawable.ic_bootstrap_node_online
                else
                    R.drawable.ic_bootstrap_node_offline
            )
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemBootstrapNodeBinding {
        return ItemBootstrapNodeBinding.inflate(inflater, parent, false)
    }
}