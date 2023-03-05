package ru.fredboy.tox4a.core.callbacks

interface ToxCoreEventListener :
    FileChunkRequestCallback,
    FileRecvCallback,
    FileRecvChunkCallback,
    FileRecvControlCallback,
    FriendConnectionStatusCallback,
    FriendLosslessPacketCallback,
    FriendLossyPacketCallback,
    FriendMessageCallback,
    FriendNameCallback,
    FriendReadReceiptCallback,
    FriendRequestCallback,
    FriendStatusCallback,
    FriendStatusMessageCallback,
    FriendTypingCallback,
    SelfConnectionStatusCallback