package ru.fredboy.toxoid.clean.presentation.view.base

import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import moxy.MvpAppCompatFragment

abstract class BaseMvpFragment : MvpAppCompatFragment() {
    fun <Model, Item : GenericItem> ModelAdapter<Model, Item>.setItemsWithDiff(models: List<Model>) {
        val diffResult =
            FastAdapterDiffUtil.calculateDiff(this, models.mapNotNull { intercept(it) })
        FastAdapterDiffUtil.set(this, diffResult)
    }
}