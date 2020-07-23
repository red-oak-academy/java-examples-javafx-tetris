package me.redoak.tetrisfx.protocol.messages.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.redoak.tetrisfx.protocol.Message;
import me.redoak.tetrisfx.protocol.MessageType;

@Data
@AllArgsConstructor
public class Init extends Message {

    private MessageType messageType;
    private String username;

    public Init() {
        messageType = MessageType.INIT;
    }
}
