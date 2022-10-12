package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class BootstrapPresenter @Inject constructor(
    private val useCases: BootstrapUseCases
) : BaseMvpPresenter<BootstrapView>() {

    override fun onFirstViewAttach() {
        presenterScope.launch {
            viewState.setNodes(useCases.getBootstrapNodes())
        }
    }

}