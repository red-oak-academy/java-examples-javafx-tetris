package me.redoak.api.ws;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Component
public class WebSocketClientRegistry {

    private final List<GameClient> gameClients = new ArrayList<>();

    public void registerGameClient(GameClient gameClient) {
        this.gameClients.add(gameClient);
    }

    public void removeClient(WebSocketSession session) {
        gameClients.stream()
                .filter(gc -> !gc.getWebSocketSession().equals(session))
                .findAny()
                .ifPresent(gameClients::remove);
    }
}
