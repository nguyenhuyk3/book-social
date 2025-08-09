package com.bs.profile.services;

import com.bs.profile.dto.requests.ProfileCreationRequest;
import com.bs.profile.dto.responses.UserProfileResponse;
import com.bs.profile.entities.UserProfile;
import com.bs.profile.exceptions.AppException;
import com.bs.profile.exceptions.ErrorCode;
import com.bs.profile.mappers.UserProfileMapper;
import com.bs.profile.repositories.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);

        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
