package ru.fredboy.toxoid.clean.presentation.view.addcontact

import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class AddContactPresenter @Inject constructor(
    private val useCases: AddContactUseCases,
) : BaseMvpPresenter<AddContactView>() {
}