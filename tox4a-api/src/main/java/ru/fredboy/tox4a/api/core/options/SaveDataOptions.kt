package ru.fredboy.tox4a.api.core.options

import ru.fredboy.tox4a.api.core.data.enums.ToxSavedataType

data class SaveDataOptions(
    val kind: ToxSavedataType,
    val data: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaveDataOptions

        if (kind != other.kind) return false
        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        var result = kind.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    companion object {
        val NONE = SaveDataOptions(
            kind = ToxSavedataType.NONE,
            data = ByteArray(0)
        )

        fun toxSave(data: ByteArray) = SaveDataOptions(
            kind = ToxSavedataType.TOX_SAVE,
            data = data
        )

        fun secretKey(data: ByteArray) = SaveDataOptions(
            kind = ToxSavedataType.SECRET_KEY,
            data = data,
        )
    }
}