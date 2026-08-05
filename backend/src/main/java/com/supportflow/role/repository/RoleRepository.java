package com.supportflow.role.repository;

import com.supportflow.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByCode(String code);
}
