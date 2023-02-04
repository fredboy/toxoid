package ru.fredboy.toxoid.clean.presentation.view.addcontact

import android.content.Context
import android.graphics.drawable.Drawable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class AddContactPresenter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val useCases: AddContactUseCases,
) : BaseMvpPresenter<AddContactView>() {

    fun sendFriendRequest(toxId: String, message: String = "Hello") {
        CoroutineScope(Dispatchers.Default).launch {
            useCases.sendFriendRequest(toxId, message)
        }
    }

    fun createIdenticonDrawable(toxId: String): Drawable {
        return useCases.createIdenticon(toxId).getDrawable(context.resources, 160)
    }

}