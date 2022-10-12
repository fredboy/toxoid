package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.model.BootstrapNodeVo
import ru.fredboy.toxoid.databinding.ItemBootstrapNodeBinding

class BootstrapNodeItem(
    private val model: BootstrapNodeVo,
    private val onSwitched: (Boolean) -> Unit,
) : AbstractBindingItem<ItemBootstrapNodeBinding>() {

    override val type = R.id.item_bootstrap_node

    override fun bindView(binding: ItemBootstrapNodeBinding, payloads: List<Any>) {
        with(binding) {
            bootstrapNodeCountry.text = model.flag
            bootstrapNodeSocket.text = model.socket
            bootstrapNodeMotd.text = model.motd

            bootstrapNodeOnlineStatus.setImageResource(
                if (model.status)
                    R.drawable.ic_bootstrap_node_online
                else
                    R.drawable.ic_bootstrap_node_offline
            )

            bootstrapNodeSwitch.setOnClickListener {
                onSwitched(bootstrapNodeSwitch.isChecked)
            }
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemBootstrapNodeBinding {
        return ItemBootstrapNodeBinding.inflate(inflater, parent, false)
    }
}