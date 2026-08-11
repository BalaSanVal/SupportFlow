package com.supportflow.role.service;

import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.mapper.RoleMapper;
import com.supportflow.role.repository.RoleRepository;
import com.supportflow.role.entity.Role;
import com.supportflow.role.validation.RoleCodeNormalizer;
import com.supportflow.role.dto.request.RoleCreateRequest;
import com.supportflow.role.exception.DuplicateRoleCodeException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RoleResponse create(RoleCreateRequest request) {
        String normalizedCode = RoleCodeNormalizer.normalize(request.code());

        if (roleRepository.existsByCode(normalizedCode)) {
            throw new DuplicateRoleCodeException(normalizedCode);
        }

        Role role = new Role(normalizedCode, request.name(), request.description());

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toResponse(savedRole);
    }
}
