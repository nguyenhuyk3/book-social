package com.bs.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bs.identity.dto.requests.RoleRequest;
import com.bs.identity.dto.responses.RoleResponse;
import com.bs.identity.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
