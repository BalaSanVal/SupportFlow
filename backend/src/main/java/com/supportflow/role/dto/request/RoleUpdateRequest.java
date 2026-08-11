package com.supportflow.role.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoleUpdateRequest(
        @NotBlank(message = "Role name is required")
        String name,

        String description
) {
}
