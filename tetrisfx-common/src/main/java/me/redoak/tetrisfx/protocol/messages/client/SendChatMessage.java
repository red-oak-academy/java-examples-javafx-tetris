package me.redoak.tetrisfx.protocol.messages.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.redoak.tetrisfx.model.ChatMessage;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageType;

@Data
@AllArgsConstructor
public class SendChatMessage extends Message {
    private MessageType messageType;
    private ChatMessage chatMessage;

    public SendChatMessage() {
        messageType = MessageType.SEND_CHAT_MESSAGE;
    }
}
