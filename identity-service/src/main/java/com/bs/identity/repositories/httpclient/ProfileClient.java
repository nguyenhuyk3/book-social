package com.bs.identity.repositories.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bs.identity.configurations.AuthenticationRequestInterceptor;
import com.bs.identity.dto.requests.ApiResponse;
import com.bs.identity.dto.requests.ProfileCreationRequest;
import com.bs.identity.dto.responses.UserProfileResponse;

//@FeignClient(
//        name = "profile-service",
//        url = "${app.services.profile}",
//        configuration = {AuthenticationRequestInterceptor.class})
@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}"
)
public interface ProfileClient {
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
