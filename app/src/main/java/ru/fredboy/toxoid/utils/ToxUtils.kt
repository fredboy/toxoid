package ru.fredboy.toxoid.utils

import im.tox.tox4j.core.ToxCoreConstants

private val TOXID_LENGTH = ToxCoreConstants.AddressSize() * 2
private val TOXID_REGEX = Regex("^([A-F\\d]){$TOXID_LENGTH}$")

fun validateToxId(toxId: String): Boolean {
    return toxId.uppercase().matches(TOXID_REGEX)
}