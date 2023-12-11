package ru.fredboy.toxoid.tox;

import android.util.Log;

import androidx.annotation.NonNull;
import im.tox.tox4j.core.callbacks.ToxCoreEventListener;
import im.tox.tox4j.core.data.ToxFriendMessage;
import im.tox.tox4j.core.data.ToxFriendNumber;
import im.tox.tox4j.core.data.ToxFriendRequestMessage;
import im.tox.tox4j.core.data.ToxNickname;
import im.tox.tox4j.core.data.ToxPublicKey;
import im.tox.tox4j.core.enums.ToxConnection;
import im.tox.tox4j.core.enums.ToxMessageType;
import ru.fredboy.toxoid.clean.data.model.tox.FriendRequestData;
import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData;
import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData;

abstract class ToxEventListenerImpl implements ToxCoreEventListener<Object> {

    private static final String TAG = "EventListenerImpl";

    @NonNull
    private final ToxServiceUseCases mUseCases;

    ToxEventListenerImpl(@NonNull final ToxServiceUseCases useCases) {
        mUseCases = useCases;
    }
    @Override
    public Object friendMessage(ToxFriendNumber friendNumber, ToxMessageType messageType, int timeDelta,
                                ToxFriendMessage message, Object state) {
        Log.d(TAG, "friendMessage: ");
        mUseCases.flowIncomingMessage(new IncomingMessageData(friendNumber.getValue(), message.getValue()));
        return new Object();
    }

    @Override
    public Object friendName(ToxFriendNumber friendNumber, ToxNickname name, Object state) {
        Log.d(TAG, "friendName: ");
        mUseCases.flowNewContactName(new NewFriendNameData(friendNumber.getValue(), name.getValue()));
        return new Object();
    }

    @Override
    public Object friendRequest(ToxPublicKey publicKey, int timeDelta, ToxFriendRequestMessage message, Object state) {
        Log.d(TAG, "friendRequest: ");
        mUseCases.broadcastNewFriendRequest(new FriendRequestData(publicKey.getValue(), message.getValue()));
        return new Object();
    }

    @Override
    public Object selfConnectionStatus(ToxConnection connectionStatus, Object state) {
        Log.d(TAG, "selfConnectionStatus: " + connectionStatus.name());
        mUseCases.streamSelfConnectionStatus(connectionStatus);
        return new Object();
    }

}

