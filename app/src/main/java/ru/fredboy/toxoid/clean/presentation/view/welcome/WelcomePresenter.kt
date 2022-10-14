package ru.fredboy.toxoid.clean.presentation.view.welcome

import ru.fredboy.toxoid.clean.domain.usecase.InitializeWithMockDataUseCase
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class WelcomePresenter @Inject constructor(
    private val initializeWithMockDataUseCase: InitializeWithMockDataUseCase
) : BaseMvpPresenter<WelcomeView>() {

    fun onContinueClick() {
//        initializeWithMockDataUseCase.execute()
    }

}