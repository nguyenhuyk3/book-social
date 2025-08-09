package com.bs.identity.mappers;

import org.mapstruct.Mapper;

import com.bs.identity.dto.requests.ProfileCreationRequest;
import com.bs.identity.dto.requests.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
