package com.supportflow.role.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "roles")

public class Role {

    @Id
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Role() {
    }

    public Role(String code, String name, String description) {
        this.code = normalizeCode(code);
        this.name = normalizeRequiredText(name);
        this.description = normalizeOptionalText(description);
        this.active = true;
    }

    @PrePersist
    private void prePersist() {
        Instant now = Instant.now();

        if (id == null) {
            id = UUID.randomUUID();
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }

    private static String normalizeCode(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role code must not be blank");
        }

        return value.trim().toUpperCase();
    }

    private static String normalizeRequiredText(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Required text must not be blank");
        }

        return value.trim();
    }

    private static String normalizeOptionalText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void updateDetails(String name, String description) {
        this.name = normalizeRequiredText(name);
        this.description = normalizeOptionalText(description);
    }

    public void activate() {
        this.active = true;
    }

    public void desactivate() {
        this.active = false;
    }
}
