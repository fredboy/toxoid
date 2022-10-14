package ru.fredboy.toxoid.clean.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalUser(
    val id: String,
    val name: String
) : Parcelable
