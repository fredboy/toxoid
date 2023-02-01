package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.presentation.model.BootstrapNodeVo
import ru.fredboy.toxoid.databinding.ItemBootstrapNodeBinding

class BootstrapNodeItem(
    model: BootstrapNodeVo,
) : ModelAbstractBindingItem<BootstrapNodeVo, ItemBootstrapNodeBinding>(
    model = model
) {

    override val type = R.id.item_bootstrap_node

    override fun bindView(binding: ItemBootstrapNodeBinding, payloads: List<Any>) {
        with(binding) {
            bootstrapNodeCountry.text = model.flag
            bootstrapNodeSocket.text = model.socket
            bootstrapNodeMotd.text = model.motd
            bootstrapNodeSwitch.isChecked = isSelected

            bootstrapNodeOnlineStatus.setImageResource(
                if (model.status)
                    R.drawable.ic_bootstrap_node_online
                else
                    R.drawable.ic_bootstrap_node_offline
            )

            bootstrapNodeSwitch.setOnClickListener {
                isSelected = bootstrapNodeSwitch.isChecked
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