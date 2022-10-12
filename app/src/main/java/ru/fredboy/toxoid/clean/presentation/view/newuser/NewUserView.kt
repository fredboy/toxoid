package ru.fredboy.toxoid.clean.presentation.view.newuser

import android.graphics.drawable.Drawable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface NewUserView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUserPhoto(drawable: Drawable)

}