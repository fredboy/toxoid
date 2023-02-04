package ru.fredboy.toxoid.clean.domain.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.scale
import com.google.common.hash.Hashing
import ru.fredboy.toxoid.BuildConfig
import kotlin.math.abs


data class Identicon(
    val publicKey: ToxPublicKey
) {
    private val bitmap: Bitmap

    private val identiconColors: Array<Array<Int>> = Array(IDENTICON_ROWS) { Array(ACTIVE_COLS) { 0 } }
    private val colors: Array<Int> = Array(COLORS) { 0 }

    init {
        @Suppress("UnstableApiUsage")
        val hash = Hashing.sha256().hashBytes(publicKey.bytes).asBytes()
            .asSequence()
            .map { it.toUByte().toInt() }
            .toList()

        for (i in colors.indices) {
            val start = hash.size - IDENTICON_COLOR_BYTES * (i + 1)
            val end = start + IDENTICON_COLOR_BYTES
            val hashpart = hash.slice(start until end)
            val hue = bytesToColor(hashpart).toFloat()
            val lig = i.toFloat() / COLORS + 0.3f
            val sat = 0.5f
            colors[i] = ColorUtils.HSLToColor(floatArrayOf(hue, sat, lig))
        }

        for (row in identiconColors.indices) {
            for (col in identiconColors[row].indices) {
                val hashIdx = row * ACTIVE_COLS + col
                val colorIndex = hash[hashIdx] % COLORS
                identiconColors[row][col] = colorIndex
            }
        }

        bitmap = toBitmap()
    }

    private fun bytesToColor(bytes: List<Int>): Double {
        if (BuildConfig.DEBUG && IDENTICON_COLOR_BYTES >= 8) {
            error("Number of color bytes must be less than 8")
        }
        if (BuildConfig.DEBUG && bytes.size != IDENTICON_COLOR_BYTES) {
            error("Hash part must have the size of IDENTICON_COLOR_BYTES")
        }

        val max = (1L shl (IDENTICON_COLOR_BYTES * 8)) - 1
        var hue = 0L
        for (i in 0 until IDENTICON_COLOR_BYTES) {
            hue = hue shl 8
            hue += bytes[i]
        }

        return hue.toDouble() / max.toDouble() * 360
    }

    private fun toBitmap(): Bitmap {
        val imageSize = IDENTICON_ROWS + 2


        val bitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(colors[0])

        for (row in 0 until IDENTICON_ROWS) {
            for (col in 0 until IDENTICON_ROWS) {
                val columnIdx: Int = abs((col * 2 - (IDENTICON_ROWS - 1)) / 2)
                val colorIdx = identiconColors[row][columnIdx]
                bitmap.setPixel(col + 1, row + 1, colors[colorIdx])
            }
        }

        return bitmap
    }

    fun getDrawable(resources: Resources, scaleFactor: Int): BitmapDrawable {
        val size = bitmap.width * if (scaleFactor >= 1) scaleFactor else 1
        return BitmapDrawable(resources, bitmap.scale(size, size, false))
    }

    companion object {
        private const val IDENTICON_COLOR_BYTES = 6
        private const val IDENTICON_ROWS = 5
        private const val COLORS = 2
        private const val ACTIVE_COLS = 3
    }
}