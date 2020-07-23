package me.redoak.api.ws;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.redoak.chat.ChatService;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageConverter;
import me.redoak.tetrisfx.protocol.MessageProcessingException;
import me.redoak.tetrisfx.protocol.messages.client.Init;
import me.redoak.tetrisfx.protocol.messages.client.SendChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {

    private final WebSocketClientRegistry webSocketClientRegistry;
    private final MessageConverter messageConverter;
    private final ChatService chatService;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketClientRegistry.removeClient(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        try {
            final Message deserializedMessage = messageConverter.deserialize(message.getPayload());
            log.info("received " + deserializedMessage.getMessageType());

            handleDelegate(session, deserializedMessage);

        } catch (MessageProcessingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDelegate(WebSocketSession webSocketSession, Message message) {
        switch (message.getMessageType()) {
            case INIT:
                handle((Init) message, webSocketSession);
                break;
            case SEND_CHAT_MESSAGE:
                handle((SendChatMessage) message);
                break;
        }
    }

    private void handle(Init initMessage, WebSocketSession webSocketSession) {
        String clientId = UUID.randomUUID().toString();
        webSocketClientRegistry.registerGameClient(new GameClient(webSocketSession, clientId));
    }

    private void handle(SendChatMessage sendChatMessage) {
        chatService.appendAndPopulateChatMessage(sendChatMessage.getChatMessage());
    }
}
