package ru.fredboy.tox4a.api.av.data

@JvmInline
value class Height(val value: Int) {

    init {
        require(value in 1..1200)
    }

}
