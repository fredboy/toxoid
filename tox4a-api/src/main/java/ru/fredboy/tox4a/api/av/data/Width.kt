package ru.fredboy.tox4a.api.av.data

@JvmInline
value class Width(val value: Int) {

    init {
        require(value in 1..1920)
    }

}