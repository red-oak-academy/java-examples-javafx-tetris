package me.redoak.tetrisfx.protocol.messages.server;

import lombok.Data;
import me.redoak.tetrisfx.model.ChatMessage;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageType;

@Data
public class ChatMessageAdded extends Message {
    private MessageType messageType;
    private ChatMessage chatMessage;

    public ChatMessageAdded() {
        messageType = MessageType.CHAT_MESSAGE_ADDED;
    }
}
