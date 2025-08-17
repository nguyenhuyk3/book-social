package com.bs.profile.controllers;

import com.bs.profile.dto.requests.ProfileCreationRequest;
import com.bs.profile.dto.responses.ApiResponse;
import com.bs.profile.dto.responses.UserProfileResponse;
import com.bs.profile.services.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request) {
        return ApiResponse
                .<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

    @GetMapping("/internal/users/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId) {
        return ApiResponse
                .<UserProfileResponse>builder()
                .result(userProfileService.getByUserId(userId))
                .build();
    }
}
