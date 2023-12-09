package ru.fredboy.tox4a.api.av.callbacks

interface ToxavEventListener :
        AudioBitRateCallback,
        AudioReceiveFrameCallback,
        CallCallback,
        CallStateCallback,
        VideoBitRateCallback,
        VideoReceiveFrameCallback