package com.bs.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bs.identity.dto.requests.UserCreationRequest;
import com.bs.identity.dto.requests.UserUpdateRequest;
import com.bs.identity.dto.responses.UserResponse;
import com.bs.identity.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
