package ru.fredboy.toxoid.utils

import im.tox.tox4j.core.ToxCoreConstants

private const val TOXID_LENGTH = ToxCoreConstants.addressSize * 2
private const val TOX_PUB_KEY_STRING_LENGTH = ToxCoreConstants.publicKeySize * 2
private val TOXID_REGEX = Regex("^([A-F\\d]){$TOXID_LENGTH}$")
private val TOX_PUB_KEY_STRING_REGEX = Regex("^([A-F\\d]){$TOX_PUB_KEY_STRING_LENGTH}$")

fun validateToxId(toxId: String): Boolean {
    return toxId.uppercase().matches(TOXID_REGEX)
}

fun validateToxPublicKeyString(toxPublicKeyString: String): Boolean {
    return toxPublicKeyString.uppercase().matches(TOX_PUB_KEY_STRING_REGEX)
}