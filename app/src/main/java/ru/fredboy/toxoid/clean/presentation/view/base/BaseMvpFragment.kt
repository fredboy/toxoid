package ru.fredboy.toxoid.clean.presentation.view.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.viewbinding.ViewBinding
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import moxy.MvpAppCompatFragment

abstract class BaseMvpFragment : MvpAppCompatFragment() {

    protected fun <Model, Item : GenericItem> ModelAdapter<Model, Item>.setItemsWithDiff(models: List<Model>) {
        val diffResult =
            FastAdapterDiffUtil.calculateDiff(this, models.mapNotNull { intercept(it) })
        FastAdapterDiffUtil.set(this, diffResult)
    }

    protected fun hideSoftInput(binding: ViewBinding) {
        val inputManager = context
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
        inputManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}