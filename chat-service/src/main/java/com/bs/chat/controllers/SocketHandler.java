package com.bs.chat.controllers;

import com.bs.chat.dto.requests.IntrospectRequest;
import com.bs.chat.services.IdentityService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SocketHandler {
    SocketIOServer server;
    IdentityService identityService;

    @OnConnect
    public void clientConnected(SocketIOClient socketIOClient) {
        // Get Token from request param
        String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
        // Verify token
        var introspectResponse = identityService
                .introspect(IntrospectRequest
                        .builder()
                        .token(token)
                        .build());

        if (introspectResponse.isValid()) {
            log.info("Client connected: {}", socketIOClient.getSessionId());
        } else {
            log.error("Authentication fail: {}", socketIOClient.getSessionId());

            socketIOClient.disconnect();
        }
    }

    @OnDisconnect
    public void clientDisconnected(SocketIOClient socketIOClient) {

    }

    /*
        Hàm này sẽ được Spring gọi ngay sau khi bean SocketHandler được khởi tạo.
    */
    @PostConstruct
    public void startServer() {
        server.start();
        server.addListeners(this);
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
    }
}
