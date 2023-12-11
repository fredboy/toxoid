package ru.fredboy.toxoid.utils

import com.fernandocejas.sample.core.functional.Either
import java.util.*

fun generateRandomStringId() = UUID.randomUUID().toString()

fun bytesToHexString(bytes: ByteArray) = bytes.joinToString("") { "%02X".format(it) }

fun hexStringToByteArray(string: String) = string.chunked(2)
    .map { it.toInt(16).toByte() }.toByteArray()

fun countryCodeToEmojiFlag(countryCode: String): String {
    return countryCode
        .uppercase()
        .asSequence()
        .map { char ->
            Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
        }
        .map { codePoint ->
            Character.toChars(codePoint)
        }
        .joinToString(separator = "") { charArray ->
            String(charArray)
        }
}

fun <T> Either<Throwable, T>.rightOrThrow(): T {
    return when(this) {
        is Either.Right -> this.right
        is Either.Left -> throw this.left
    }
}

inline fun <reified T> T.exhaustive(): T {
    return this
}