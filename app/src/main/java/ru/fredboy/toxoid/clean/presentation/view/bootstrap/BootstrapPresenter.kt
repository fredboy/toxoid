package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class BootstrapPresenter @Inject constructor(
    private val useCases: BootstrapUseCases
) : BaseMvpPresenter<BootstrapView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

}