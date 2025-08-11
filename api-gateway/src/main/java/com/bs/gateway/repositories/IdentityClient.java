package com.bs.gateway.repositories;


import com.bs.gateway.dto.requests.IntrospectRequest;
import com.bs.gateway.dto.responses.ApiResponse;
import com.bs.gateway.dto.responses.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Component
public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
