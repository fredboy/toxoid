package ru.fredboy.toxoid.tox;

import android.util.Log;

import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;
import im.tox.tox4j.core.callbacks.ToxCoreEventListener;
import im.tox.tox4j.core.enums.ToxConnection;
import im.tox.tox4j.core.enums.ToxFileControl;
import im.tox.tox4j.core.enums.ToxMessageType;
import im.tox.tox4j.core.enums.ToxUserStatus;
import kotlinx.coroutines.Dispatchers;
import ru.fredboy.toxoid.clean.data.model.FriendRequestData;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

class ToxEventListenerImpl implements ToxCoreEventListener<Object> {

    private static final String TAG = "EventListenerImpl";

    @NonNull
    private final ToxServiceUseCases mUseCases;

    @NonNull
    private final IGetFriendIdByNumber mGetFriendIdByNumber;

    @Deprecated
            // TODO: 11/20/22 move to frind number usage in domain
    interface IGetFriendIdByNumber {

        String get(int number);
    }

    ToxEventListenerImpl(@NonNull final ToxServiceUseCases useCases,
            @NonNull final IGetFriendIdByNumber getFriendIdByNumber) {
        mUseCases = useCases;
        mGetFriendIdByNumber = getFriendIdByNumber;
    }

    @Override
    public Object fileChunkRequest(int friendNumber, int fileNumber, long position, int length,
            Object state) {
        Log.d(TAG, "fileChunkRequest: ");
        return new Object();
    }

    @Override
    public Object fileRecv(int friendNumber, int fileNumber, int kind, long fileSize,
            byte[] filename, Object state) {
        Log.d(TAG, "fileRecv: ");
        return new Object();
    }

    @Override
    public Object fileRecvChunk(int friendNumber, int fileNumber, long position, byte[] data,
            Object state) {
        Log.d(TAG, "fileRecvChunk: ");
        return new Object();
    }

    @Override
    public Object fileRecvControl(int friendNumber, int fileNumber, ToxFileControl control,
            Object state) {
        Log.d(TAG, "fileRecvControl: ");
        return new Object();
    }

    @Override
    public Object friendConnectionStatus(int friendNumber, ToxConnection connectionStatus,
            Object state) {
        Log.d(TAG, "friendConnectionStatus: ");
        return new Object();
    }

    @Override
    public Object friendLosslessPacket(int friendNumber, byte[] data, Object state) {
        Log.d(TAG, "friendLosslessPacket: ");
        return new Object();
    }

    @Override
    public Object friendLossyPacket(int friendNumber, byte[] data, Object state) {
        Log.d(TAG, "friendLossyPacket: ");
        return new Object();
    }

    @Override
    public Object friendMessage(int friendNumber, ToxMessageType messageType, int timeDelta,
            byte[] message, Object state) {
        Log.d(TAG, "friendMessage: ");
        return new Object();
    }

    @Override
    public Object friendName(int friendNumber, byte[] name, Object state) {
        Log.d(TAG, "friendName: ");
        mUseCases.setContactName(mGetFriendIdByNumber.get(friendNumber), new String(name, StandardCharsets.UTF_8));
        return new Object();
    }

    @Override
    public Object friendReadReceipt(int friendNumber, int messageId, Object state) {
        Log.d(TAG, "friendReadReceipt: ");
        return new Object();
    }

    @Override
    public Object friendRequest(byte[] publicKey, int timeDelta, byte[] message, Object state) {
        Log.d(TAG, "friendRequest: ");
        mUseCases.broadcastNewFriendRequest(new FriendRequestData(publicKey, message));
        return new Object();
    }

    @Override
    public Object friendStatus(int friendNumber, ToxUserStatus status, Object state) {
        Log.d(TAG, "friendStatus: ");
        return new Object();
    }

    @Override
    public Object friendStatusMessage(int friendNumber, byte[] message, Object state) {
        Log.d(TAG, "friendStatusMessage: ");
        return new Object();
    }

    @Override
    public Object friendTyping(int friendNumber, boolean isTyping, Object state) {
        Log.d(TAG, "friendTyping: ");
        return new Object();
    }

    @Override
    public Object selfConnectionStatus(ToxConnection connectionStatus, Object state) {
        Log.d(TAG, "selfConnectionStatus: ");
        mUseCases.streamSelfConnectionStatus(connectionStatus);
        return new Object();
    }

}

