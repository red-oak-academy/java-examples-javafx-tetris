package me.redoak.tetrisfx.network;

import lombok.extern.slf4j.Slf4j;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageConverter;
import me.redoak.tetrisfx.protocol.MessageProcessingException;
import me.redoak.tetrisfx.protocol.messages.server.ChatMessageAdded;
import me.redoak.tetrisfx.protocol.messages.server.InitSuccess;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebSocketConnectionClient extends WebSocketClient {

    private final List<ConnectionEventListener> connectionEventListeners = new ArrayList<>();
    private final List<ChatEventListener> chatEventListenerList = new ArrayList<>();
    private MessageConverter messageConverter = new MessageConverter();

    public WebSocketConnectionClient(URI serverUri) {
        super(serverUri);
    }

    public void register(ChatEventListener chatEventListener) {
        this.chatEventListenerList.add(chatEventListener);
    }

    public void register(ConnectionEventListener connectionEventListener) {
        this.connectionEventListeners.add(connectionEventListener);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("Opened Connection to server.");
        connectionEventListeners.forEach(ConnectionEventListener::onConnectionOpened);
    }

    @Override
    public void onMessage(String s) {
        Message message = null;
        try {
            message = messageConverter.deserialize(s);

            switch (message.getMessageType()) {
                case INIT_SUCCESS:
                    this.handle((InitSuccess) message);
                    break;
                case CHAT_MESSAGE_ADDED:
                    this.handle((ChatMessageAdded) message);
                    break;
                default:
                    log.error("received unhandled message of type " + message.getMessageType());
            }

        } catch (MessageProcessingException e) {
            log.error("could not deserialize received Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handle(ChatMessageAdded message) {
        this.chatEventListenerList.forEach(listener -> listener.onReceivedChatMessage(message.getChatMessage()));
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("Closed Connection to server.");
    }

    @Override
    public void onError(Exception e) {
        log.error("onError fired.");
    }


    public void handle(InitSuccess initSuccess) {
        this.connectionEventListeners.forEach(el -> el.onInitSuccess(initSuccess.getClientId()));
    }

    public void send(Message initMessage) throws MessageProcessingException {
        final String serializedMessage = messageConverter.serialize(initMessage);
        this.send(serializedMessage);
    }
}
