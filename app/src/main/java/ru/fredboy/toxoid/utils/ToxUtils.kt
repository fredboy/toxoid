package ru.fredboy.toxoid.utils

typealias ToxId = ByteArray

private const val TOXID_LENGTH = 76
private val TOXID_REGEX = Regex("^([A-Z0-9]){$TOXID_LENGTH}$")

fun validateToxId(toxId: String): Boolean {
    return toxId.uppercase().matches(TOXID_REGEX)
}