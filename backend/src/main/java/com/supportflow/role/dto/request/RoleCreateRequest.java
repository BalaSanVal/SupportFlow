package com.supportflow.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleCreateRequest(

        @NotBlank(message = "Role code is required")
        @Size(max = 50, message = "Role code must not exceed 50 characters")
        String code,

        @NotBlank(message = "Role name is required")
        @Size(max = 100, message = "Role name must not exceed 100 characters")
        String name,

        @Size(max = 255, message = "Role description must not exceed 255 characters")
        String description
) {

}
