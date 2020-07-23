package me.redoak.api.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
public class GameClient {
    private WebSocketSession webSocketSession;
    private String clientId;
}
