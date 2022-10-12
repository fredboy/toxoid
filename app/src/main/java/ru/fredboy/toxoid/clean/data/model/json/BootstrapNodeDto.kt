package ru.fredboy.toxoid.clean.data.model.json

import com.google.gson.annotations.SerializedName

data class BootstrapNodeDto(
    val ipv4: String? = null,
    val ipv6: String? = null,
    val port: Int? = null,
    @SerializedName("public_key") val publicKey: String? = null,
    val maintainer: String? = null,
    val location: String? = null,
    @SerializedName("status_udp") val statusUdp: Boolean? = null,
    @SerializedName("status_tcp") val statusTcp: Boolean? = null,
    val version: String? = null,
    val motd: String? = null,
    @SerializedName("last_ping") val lastPing: Long? = null,
)
