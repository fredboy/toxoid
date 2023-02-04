package ru.fredboy.toxoid.utils

import im.tox.tox4j.core.ToxCoreConstants

private val TOXID_LENGTH = ToxCoreConstants.AddressSize() * 2
private val TOX_PUB_KEY_STRING_LENGTH = ToxCoreConstants.PublicKeySize() * 2
private val TOXID_REGEX = Regex("^([A-F\\d]){$TOXID_LENGTH}$")
private val TOX_PUB_KEY_STRING_REGEX = Regex("^([A-F\\d]){$TOX_PUB_KEY_STRING_LENGTH}$")

fun validateToxId(toxId: String): Boolean {
    return toxId.uppercase().matches(TOXID_REGEX)
}

fun validateToxPublicKeyString(toxPublicKeyString: String): Boolean {
    return toxPublicKeyString.uppercase().matches(TOX_PUB_KEY_STRING_REGEX)
}