package com.supportflow.role.exception;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(UUID id) {
        super("Role with id '" + id + "' was not found");
    }
}
