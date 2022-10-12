package ru.fredboy.toxoid.utils

import java.util.*

fun generateRandomStringId() = UUID.randomUUID().toString()

fun bytesToHexString(bytes: ByteArray) = bytes.joinToString("") { "%02X".format(it) }

fun hexStringToByteArray(string: String) = string.chunked(2)
    .map { it.toInt(16).toByte() }.toByteArray()