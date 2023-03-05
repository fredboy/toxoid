package ru.fredboy.tox4a.jni;

import org.jetbrains.annotations.NotNull;

public final class ToxCoreJni {

    static {
        System.loadLibrary("tox4a");
    }

    static native int toxNew(
            boolean ipv6Enabled,
            boolean udpEnabled,
            boolean localDiscoveryEnabled,
            int proxyType,
            @NotNull String proxyAddress,
            int proxyPort,
            int startPort,
            int endPort,
            int tcpPort,
            int saveDataType,
            @NotNull byte[] saveData
    );

}
