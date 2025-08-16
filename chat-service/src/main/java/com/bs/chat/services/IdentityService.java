package com.bs.chat.services;

import com.bs.chat.dto.requests.IntrospectRequest;
import com.bs.chat.dto.responses.IntrospectResponse;
import com.bs.chat.repositories.httpClient.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            var result = identityClient.introspect(request).getResult();

            if (Objects.isNull(result)) {
                return IntrospectResponse
                        .builder()
                        .valid(false)
                        .build();
            }

            return result;
        } catch (Exception e) {
            return IntrospectResponse
                    .builder()
                    .valid(false)
                    .build();
        }
    }
}
