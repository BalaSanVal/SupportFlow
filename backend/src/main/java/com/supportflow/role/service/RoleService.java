package com.supportflow.role.service;

import com.supportflow.role.repository.RoleRepository;
import com.supportflow.role.entity.Role;
import com.supportflow.role.validation.RoleCodeNormalizer;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role create(String code, String name, String description) {
        String normalizedCode = RoleCodeNormalizer.normalize(code);

        if (roleRepository.existsByCode(normalizedCode)) {
            throw new IllegalStateException("A rol with code '" + normalizedCode + "' already exists");
        }

        Role role = new Role(normalizedCode, name, description);

        return roleRepository.save(role);
    }
}
