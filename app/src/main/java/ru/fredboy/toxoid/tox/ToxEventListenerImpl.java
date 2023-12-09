package ru.fredboy.toxoid.tox;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import ru.fredboy.tox4a.api.core.callbacks.ToxCoreEventListener;
//import ru.fredboy.tox4a.api.core.data.ToxFileName;
//import ru.fredboy.tox4a.api.core.data.ToxFriendNumber;
//import ru.fredboy.tox4a.api.core.data.enums.ToxFileKind;
//import ru.fredboy.toxoid.clean.data.model.tox.FriendRequestData;
//import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData;
//import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData;
//
class ToxEventListenerImpl /*implements ToxCoreEventListener*/ {
//
//    private static final String TAG = "EventListenerImpl";
//
//    @NonNull
//    private final ToxServiceUseCases mUseCases;
//
//    ToxEventListenerImpl(@NonNull final ToxServiceUseCases useCases) {
//        mUseCases = useCases;
//    }
//
//    @Override
//    public void fileChunkRequest(ToxFriendNumber friendNumber, int fileNumber, long position, int length) {
//        Log.d(TAG, "fileChunkRequest: ");
//
//    }
//
//    @Override
//    public void fileRecv(ToxFriendNumber friendNumber, int fileNumber, ToxFileKind kind, long fileSize,
//            ToxFileName filename) {
//        Log.d(TAG, "fileRecv: ");
//
//    }
//    @Override
//    public void fileRecvChunk(ToxFriendNumber friendNumber, int fileNumber, long position, byte[] data,
//            Object state) {
//        Log.d(TAG, "fileRecvChunk: ");
//
//    }
//
//    @Override
//    public void fileRecvControl(ToxFriendNumber friendNumber, int fileNumber, ToxFileControl control,
//            Object state) {
//        Log.d(TAG, "fileRecvControl: ");
//
//    }
//
//    @Override
//    public void friendConnectionStatus(ToxFriendNumber friendNumber, ToxConnection connectionStatus,
//            Object state) {
//        Log.d(TAG, "friendConnectionStatus: ");
//
//    }
//
//    @Override
//    public void friendLosslessPacket(ToxFriendNumber friendNumber, byte[] data, Object state) {
//        Log.d(TAG, "friendLosslessPacket: ");
//
//    }
//
//    @Override
//    public void friendLossyPacket(ToxFriendNumber friendNumber, byte[] data, Object state) {
//        Log.d(TAG, "friendLossyPacket: ");
//
//    }
//
//    @Override
//    public void friendMessage(ToxFriendNumber friendNumber, ToxMessageType messageType, int timeDelta,
//            byte[] message, Object state) {
//        Log.d(TAG, "friendMessage: ");
//        mUseCases.flowIncomingMessage(new IncomingMessageData(friendNumber, message));
//
//    }
//
//    @Override
//    public void friendName(ToxFriendNumber friendNumber, byte[] name, Object state) {
//        Log.d(TAG, "friendName: ");
//        mUseCases.flowNewContactName(new NewFriendNameData(friendNumber, name));
//
//    }
//
//    @Override
//    public void friendReadReceipt(ToxFriendNumber friendNumber, int messageId, Object state) {
//        Log.d(TAG, "friendReadReceipt: ");
//
//    }
//
//    @Override
//    public void friendRequest(byte[] publicKey, int timeDelta, byte[] message, Object state) {
//        Log.d(TAG, "friendRequest: ");
//        mUseCases.broadcastNewFriendRequest(new FriendRequestData(publicKey, message));
//
//    }
//
//    @Override
//    public void friendStatus(ToxFriendNumber friendNumber, ToxUserStatus status, Object state) {
//        Log.d(TAG, "friendStatus: ");
//
//    }
//
//    @Override
//    public void friendStatusMessage(ToxFriendNumber friendNumber, byte[] message, Object state) {
//        Log.d(TAG, "friendStatusMessage: ");
//
//    }
//
//    @Override
//    public void friendTyping(ToxFriendNumber friendNumber, boolean isTyping, Object state) {
//        Log.d(TAG, "friendTyping: ");
//
//    }
//
//    @Override
//    public void selfConnectionStatus(ToxConnection connectionStatus, Object state) {
//        Log.d(TAG, "selfConnectionStatus: ");
//        mUseCases.streamSelfConnectionStatus(connectionStatus);
//
//    }
//
}
//
