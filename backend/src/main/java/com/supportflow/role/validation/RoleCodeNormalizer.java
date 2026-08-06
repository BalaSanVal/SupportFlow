package com.supportflow.role.validation;

import java.util.Locale;

public class RoleCodeNormalizer {

    private RoleCodeNormalizer() {
        throw new IllegalStateException("RoleCodeNormalizer is a utility class and cannot be instantiated");
    }

    public static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role code must not be blank");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
