package com.supportflow.role.controller;

import com.supportflow.role.dto.request.RoleCreateRequest;
import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.entity.Role;
import com.supportflow.role.service.RoleService;
import com.supportflow.role.dto.request.RoleUpdateRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(
            @Valid @RequestBody RoleCreateRequest request
    ) {
        RoleResponse response = roleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {
        List<RoleResponse> roles = roleService.findAll();

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(
            @PathVariable UUID id
    ) {
        RoleResponse response = roleService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody RoleUpdateRequest request
    ) {
        RoleResponse response = roleService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<RoleResponse> activate(
            @PathVariable UUID id
    ) {
        RoleResponse response = roleService.activate(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<RoleResponse> desactivate(
            @PathVariable UUID id
    ) {
        RoleResponse response = roleService.desactivate(id);

        return ResponseEntity.ok(response);
    }
}
