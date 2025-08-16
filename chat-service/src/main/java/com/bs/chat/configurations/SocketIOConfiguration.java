package com.bs.chat.configurations;


import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfiguration {
    @Value("${websocket.port}")
    private int WEBSOCKET_PORT;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();

        configuration.setPort(WEBSOCKET_PORT);
        configuration.setOrigin("*");

        return new SocketIOServer(configuration);
    }
}
