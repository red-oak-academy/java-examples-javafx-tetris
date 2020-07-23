package me.redoak.api.ws;

import lombok.AllArgsConstructor;
import me.redoak.constants.PathConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final GameSocketHandler gameSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameSocketHandler, PathConstants.GAME_SOCKET_PATH);
    }

}
