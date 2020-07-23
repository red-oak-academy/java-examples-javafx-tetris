package me.redoak.chat;

import lombok.AllArgsConstructor;
import me.redoak.api.ws.GameClient;
import me.redoak.api.ws.GameSocketHandler;
import me.redoak.api.ws.WebSocketClientRegistry;
import me.redoak.tetrisfx.model.ChatMessage;
import me.redoak.tetrisfx.protocol.MessageConverter;
import me.redoak.tetrisfx.protocol.MessageProcessingException;
import me.redoak.tetrisfx.protocol.messages.server.ChatMessageAdded;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    private final MessageConverter messageConverter;
    private final WebSocketClientRegistry webSocketClientRegistry;
    private final List<ChatMessage> chatMessages = new ArrayList<>();


    public void appendAndPopulateChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);

        ChatMessageAdded chatMessageAdded = new ChatMessageAdded();
        chatMessageAdded.setChatMessage(chatMessage);

        try {
            for (GameClient gameClient : webSocketClientRegistry.getGameClients()) {
                gameClient.getWebSocketSession().sendMessage(
                        new TextMessage(messageConverter.serialize(chatMessageAdded))
                );
            }
        } catch (MessageProcessingException | IOException e) {
            e.printStackTrace();
        }
    }
}
