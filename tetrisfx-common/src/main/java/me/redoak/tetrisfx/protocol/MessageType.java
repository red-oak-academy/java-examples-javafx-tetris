package me.redoak.tetrisfx.protocol;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import me.redoak.tetrisfx.protocol.messages.client.Init;
import me.redoak.tetrisfx.protocol.messages.client.SendChatMessage;
import me.redoak.tetrisfx.protocol.messages.server.ChatMessageAdded;
import me.redoak.tetrisfx.protocol.messages.server.InitSuccess;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum MessageType {

    //Initialisation
    INIT(Init.class), INIT_SUCCESS(InitSuccess.class),

    //Chat Protocol
    SEND_CHAT_MESSAGE(SendChatMessage.class), CHAT_MESSAGE_ADDED(ChatMessageAdded.class);

    @Getter
    private Class<? extends Message> targetClass;

    MessageType(Class<? extends Message> targetClass) {
        this.targetClass = targetClass;
    }
}
