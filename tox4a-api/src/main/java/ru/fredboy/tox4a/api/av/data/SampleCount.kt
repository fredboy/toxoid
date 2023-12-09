package ru.fredboy.tox4a.api.av.data

@JvmInline
value class SampleCount private constructor(val value: Int) {

    constructor(audioLength: AudioLength, samplingRate: SamplingRate) : this(
        samplingRate.value / 1000 * audioLength.value.inWholeMilliseconds.toInt()
    )

}