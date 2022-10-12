package ru.fredboy.toxoid.clean.data.model.json

import com.google.gson.annotations.SerializedName

data class BootstrapNodeDto(
    @SerializedName("ipv4") val ipv4: String?,
    @SerializedName("ipv6") val ipv6: String?,
    @SerializedName("port") val port: Int?,
    @SerializedName("public_key") val publicKey: String?,
    @SerializedName("maintainer") val maintainer: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("status_udp") val statusUdp: Boolean?,
    @SerializedName("status_tcp") val statusTcp: Boolean?,
    @SerializedName("version") val version: String?,
    @SerializedName("motd") val motd: String?,
    @SerializedName("last_ping") val lastPing: Long?,
)
