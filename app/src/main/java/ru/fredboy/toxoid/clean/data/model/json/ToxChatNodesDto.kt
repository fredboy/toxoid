package ru.fredboy.toxoid.clean.data.model.json

import com.google.gson.annotations.SerializedName

data class ToxChatNodesDto(
    @SerializedName("last_scan") val lastScan: Long? = null,
    @SerializedName("last_refresh") val lastRefresh: Long? = null,
    val nodes: List<BootstrapNodeDto>? = null,
)
