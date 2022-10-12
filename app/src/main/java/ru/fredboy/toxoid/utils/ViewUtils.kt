package ru.fredboy.toxoid.utils

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import ru.fredboy.toxoid.R

inline fun View.visible() {
    if (this.visibility == View.VISIBLE) {
        return
    }
    this.visibility = View.VISIBLE
}

inline fun View.gone() {
    if (this.visibility == View.GONE) {
        return
    }
    this.visibility = View.GONE
}

fun RecyclerView.scrollToBottom() {
    val adapter = this.adapter?.takeIf { it.itemCount > 0 } ?: return
    this.scrollToPosition(adapter.itemCount.dec())
}
