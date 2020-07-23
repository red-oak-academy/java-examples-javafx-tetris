package me.redoak.tetrisfx.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.redoak.constants.PathConstants;
import me.redoak.tetrisfx.model.ChatMessage;
import me.redoak.tetrisfx.network.ChatEventListener;
import me.redoak.tetrisfx.network.ConnectionEventListener;
import me.redoak.tetrisfx.network.WebSocketConnectionClient;
import me.redoak.tetrisfx.protocol.MessageProcessingException;
import me.redoak.tetrisfx.protocol.messages.client.Init;
import me.redoak.tetrisfx.protocol.messages.client.SendChatMessage;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class TetrisFXController implements ConnectionEventListener {

    private String username;
    private String clientId;
    private WebSocketConnectionClient webSocketConnectionClient;

    @SneakyThrows
    public void prepareConnection(String serverAddress, String username) {
        this.username = username;
        webSocketConnectionClient = new WebSocketConnectionClient(buildWebSocketUrl(serverAddress));
        webSocketConnectionClient.register(this);
    }

    public void establishConnection() {
        webSocketConnectionClient.connect();
    }


    public void registerChatEventListener(ChatEventListener chatEventListener) {
        this.webSocketConnectionClient.register(chatEventListener);
    }

    public void registerConnectionEventListener(ConnectionEventListener connectionEventListener) {
        this.webSocketConnectionClient.register(connectionEventListener);
    }

    private URI buildWebSocketUrl(String serverAddress) throws URISyntaxException {
        return new URI("ws://" + serverAddress + PathConstants.GAME_SOCKET_PATH);
    }

    public void sendChatMessage(String message) {
        SendChatMessage sendChatMessage = new SendChatMessage();
        sendChatMessage.setChatMessage(new ChatMessage(this.username, this.clientId, message));

        try {
            webSocketConnectionClient.send(sendChatMessage);
        } catch (MessageProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitSuccess(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void onConnectionOpened() {
        Init init = new Init();
        init.setUsername(username);

        try {
            this.webSocketConnectionClient.send(init);
        } catch (MessageProcessingException e) {
            log.error("Error while sending init message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
