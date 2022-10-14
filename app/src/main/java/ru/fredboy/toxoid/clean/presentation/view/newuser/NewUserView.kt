package ru.fredboy.toxoid.clean.presentation.view.newuser

import android.graphics.drawable.Drawable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.fredboy.toxoid.clean.domain.model.LocalUser

interface NewUserView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUserPhoto(drawable: Drawable)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setNewUser(user: LocalUser)

}