package com.supportflow.role.mapper;

import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.entity.Role;

public final class RoleMapper {

    private RoleMapper() {
        throw new IllegalStateException("RoleMapper is a utility class and cannot be instantiated");
    }

    public static RoleResponse toResponse(Role role){
        return new RoleResponse(
                role.getId(),
                role.getCode(),
                role.getName(),
                role.getDescription(),
                role.isActive(),
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
