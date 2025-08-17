package com.bs.gateway.configurations;

import com.bs.gateway.dto.responses.ApiResponse;
import com.bs.gateway.services.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    /*
        Serialize (Java object → JSON string)
        Deserialize (JSON string → Java object)
    */
    ObjectMapper objectMapper;

    @NonFinal
    private String[] publicEndpoints = {
            "/identity/auth/.*",
            "/identity/users/registration",
            "/notification/email/send",
            "/file/media/download/.*"
    };

    @Value("${app.api-prefix}")
    @NonFinal
    private String API_PREFIX;

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays
                .stream(publicEndpoints)
                .anyMatch(s -> request
                        .getURI()
                        .getPath()
                        .matches(API_PREFIX + s));
    }

    /*
        Mono là một kiểu dữ liệu reactive trong Project Reactor (Spring WebFlux) đại diện cho 0 hoặc 1 giá trị được trả về bất đồng bộ.
    */
    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse
                .builder()
                .code(1401)
                .message("Unauthenticated")
                .build();
        String body = null;

        try {
            // Object → JSON
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        /*
                - body.getBytes(): Chuyển JSON string thành mảng byte.
                - response.bufferFactory().wrap(...): Tạo một DataBuffer chứa mảng byte này.
                - Mono.just(...): Tạo một Mono<DataBuffer> phát ra đúng 1 giá trị là buffer vừa tạo.
                - response.writeWith(Mono<DataBuffer>): Ghi dữ liệu trong DataBuffer vào HTTP response một cách
            bất đồng bộ và trả về Mono<Void> báo hiệu quá trình ghi đã hoàn tất.
        */
        /*
            Mono<Void> đại diện cho một tác vụ bất đồng bộ mà không phát ra giá trị nào, chỉ báo hiệu hoàn thành hoặc lỗi.
            response.writeWith(...) → trả về Mono<Void>
        */
        return response.writeWith(Mono.just(response
                .bufferFactory()
                .wrap(body.getBytes())));
    }

    /*
            1. Ngữ cảnh
                - Đây là một Gateway Filter trong Spring Cloud Gateway hoặc Spring WebFlux Gateway.
                - Hàm filter() được gọi cho mỗi request đi qua gateway.
                - Mục tiêu: kiểm tra token (JWT hoặc tương tự) để xác thực người dùng.
    */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
            Kiểm tra nếu là public api thì sẽ được qua filter này
        */
        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (CollectionUtils.isEmpty(authHeader)) {
            return unauthenticated(exchange.getResponse());
        }

        String token = authHeader.getFirst().replace("Bearer ", "");

        return identityService
                .introspect(token)
                .flatMap(introspectResponse -> {
                    if (introspectResponse.getResult().isValid()) {
                        return chain.filter(exchange);
                    } else {
                        return unauthenticated(exchange.getResponse());
                    }
                })
                .onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
