package com.bs.gateway.services;

import com.bs.gateway.dto.requests.IntrospectRequest;
import com.bs.gateway.dto.responses.ApiResponse;
import com.bs.gateway.dto.responses.IntrospectResponse;
import com.bs.gateway.repositories.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return identityClient
                .introspect(
                        IntrospectRequest
                                .builder()
                                .token(token)
                                .build());
    }
}
