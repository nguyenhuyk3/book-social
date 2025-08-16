package com.bs.chat.repositories.httpClient;

import com.bs.chat.dto.requests.IntrospectRequest;
import com.bs.chat.dto.responses.ApiResponse;
import com.bs.chat.dto.responses.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "identity-client", url = "${app.services.identity.url}")
public interface IdentityClient {
    @PostMapping("/auth/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request);
}
