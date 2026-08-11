package com.supportflow.role.service;

import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.mapper.RoleMapper;
import com.supportflow.role.repository.RoleRepository;
import com.supportflow.role.entity.Role;
import com.supportflow.role.validation.RoleCodeNormalizer;
import com.supportflow.role.dto.request.RoleCreateRequest;
import com.supportflow.role.exception.DuplicateRoleCodeException;
import com.supportflow.role.exception.RoleNotFoundException;
import com.supportflow.role.dto.request.RoleUpdateRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream().map(RoleMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public RoleResponse findById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));

        return RoleMapper.toResponse(role);
    }

    @Transactional
    public RoleResponse update(UUID id, RoleUpdateRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));

        role.updateDetails(
                request.name(),
                request.description()
        );

        Role updateRole = roleRepository.save(role);

        return RoleMapper.toResponse(updateRole);
    }

    @Transactional
    public RoleResponse activate(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));

        role.activate();

        Role updateRole = roleRepository.save(role);

        return RoleMapper.toResponse(updateRole);
    }

    @Transactional
    public RoleResponse desactivate(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));

        role.desactivate();;

        Role updateRole = roleRepository.save(role);

        return RoleMapper.toResponse(updateRole);
    }
}
