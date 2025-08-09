package com.bs.profile.mappers;


import com.bs.profile.dto.requests.ProfileCreationRequest;
import com.bs.profile.dto.requests.UpdateProfileRequest;
import com.bs.profile.dto.responses.UserProfileResponse;
import com.bs.profile.entities.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);

    void update(@MappingTarget UserProfile entity, UpdateProfileRequest request);
}