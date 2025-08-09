package com.bs.identity.mappers;

import org.mapstruct.Mapper;

import com.bs.identity.dto.requests.PermissionRequest;
import com.bs.identity.dto.responses.PermissionResponse;
import com.bs.identity.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
