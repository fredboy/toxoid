package ru.fredboy.toxoid.utils

import im.tox.tox4j.core.ToxCoreConstants

typealias ToxId = ByteArray

private val TOXID_LENGTH = ToxCoreConstants.AddressSize() * 2
private val TOXID_REGEX = Regex("^([A-Z0-9]){$TOXID_LENGTH}$")

fun validateToxId(toxId: String): Boolean {
    return toxId.uppercase().matches(TOXID_REGEX)
}