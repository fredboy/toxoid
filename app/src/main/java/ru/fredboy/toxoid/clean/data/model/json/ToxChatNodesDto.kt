package ru.fredboy.toxoid.clean.data.model.json

import com.google.gson.annotations.SerializedName

data class ToxChatNodesDto(
    @SerializedName("last_scan") val lastScan: Long?,
    @SerializedName("last_refresh") val lastRefresh: Long?,
    @SerializedName("nodes") val nodes: List<BootstrapNodeDto>?,
)
