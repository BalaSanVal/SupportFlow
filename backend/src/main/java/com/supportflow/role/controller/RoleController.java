package com.supportflow.role.controller;

import com.supportflow.role.dto.request.RoleCreateRequest;
import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.service.RoleService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
