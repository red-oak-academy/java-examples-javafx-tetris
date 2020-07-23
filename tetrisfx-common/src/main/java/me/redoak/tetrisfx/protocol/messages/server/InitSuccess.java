package me.redoak.tetrisfx.protocol.messages.server;

import lombok.Data;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageType;

@Data
public class InitSuccess extends Message {

    private final MessageType messageType = MessageType.INIT_SUCCESS;
    private String clientId;
    private String username;
}
