package com.supportflow.role.service;

import com.supportflow.role.repository.RoleRepository;
import com.supportflow.role.dto.request.RoleCreateRequest;
import com.supportflow.role.dto.response.RoleResponse;
import com.supportflow.role.entity.Role;
import com.supportflow.role.exception.DuplicateRoleCodeException;
import com.supportflow.role.exception.RoleNotFoundException;
import com.supportflow.role.dto.request.RoleUpdateRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    void shouldCreateRoleWhenCodeDoesNotExist() {
        RoleCreateRequest request = new RoleCreateRequest(
                "admin",
                "Administrador",
                "Administrador general del sistema"
        );

        when(roleRepository.existsByCode("ADMIN")).thenReturn(false);

        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoleResponse response = roleService.create(request);

        assertEquals("ADMIN", response.code());
        assertEquals("Administrador", response.name());
        assertEquals("Administrador general del sistema", response.description());
        assertEquals(true, response.active());

        verify(roleRepository).existsByCode("ADMIN");
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void shouldThrowExceptionWhenRoleCodeAlreadyExists() {

        RoleCreateRequest request = new RoleCreateRequest(
                " admin ",
                "Administrador",
                "Administrador general del sistema"
        );

        when(roleRepository.existsByCode("ADMIN"))
                .thenReturn(true);

        assertThrows(
                DuplicateRoleCodeException.class,
                () -> roleService.create(request)
        );

        verify(roleRepository).existsByCode("ADMIN");

        verify(roleRepository, never())
                .save(any(Role.class));
    }

    @Test
    void shouldReturnRoleWhenIdExists() {

        UUID id = UUID.randomUUID();

        Role role = new Role(
                "USER",
                "Usuario",
                "Usuario general del sistema"
        );

        when(roleRepository.findById(id))
                .thenReturn(Optional.of(role));

        RoleResponse response = roleService.findById(id);

        assertEquals("USER", response.code());
        assertEquals("Usuario", response.name());
        assertEquals("Usuario general del sistema", response.description());
        assertEquals(true, response.active());

        verify(roleRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenRoleIdDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(roleRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RoleNotFoundException.class,
                () -> roleService.findById(id)
        );

        verify(roleRepository).findById(id);
    }

    @Test
    void shouldUpdateRoleWhenIdExists() {

        UUID id = UUID.randomUUID();

        Role existingRole = new Role(
                "USER",
                "Usuario",
                "Usuario general del sistema"
        );

        RoleUpdateRequest request = new RoleUpdateRequest(
                "Usuario estándar",
                "Usuario con permisos generales"
        );

        when(roleRepository.findById(id))
                .thenReturn(Optional.of(existingRole));

        when(roleRepository.save(any(Role.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RoleResponse response = roleService.update(id, request);

        assertEquals("USER", response.code());
        assertEquals("Usuario estándar", response.name());
        assertEquals(
                "Usuario con permisos generales",
                response.description()
        );

        verify(roleRepository).findById(id);
        verify(roleRepository).save(existingRole);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingRole() {

        UUID id = UUID.randomUUID();

        RoleUpdateRequest request = new RoleUpdateRequest(
                "Usuario estándar",
                "Usuario con permisos generales"
        );

        when(roleRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RoleNotFoundException.class,
                () -> roleService.update(id, request)
        );

        verify(roleRepository).findById(id);

        verify(roleRepository, never())
                .save(any(Role.class));
    }

    @Test
    void shouldDeactivateRoleWhenIdExists() {

        UUID id = UUID.randomUUID();

        Role role = new Role(
                "USER",
                "Usuario",
                "Usuario general del sistema"
        );

        when(roleRepository.findById(id))
                .thenReturn(Optional.of(role));

        when(roleRepository.save(any(Role.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RoleResponse response = roleService.desactivate(id);

        assertEquals(false, response.active());

        verify(roleRepository).findById(id);
        verify(roleRepository).save(role);
    }

    @Test
    void shouldActivateRoleWhenIdExists() {

        UUID id = UUID.randomUUID();

        Role role = new Role(
                "USER",
                "Usuario",
                "Usuario general del sistema"
        );

        role.desactivate();

        when(roleRepository.findById(id))
                .thenReturn(Optional.of(role));

        when(roleRepository.save(any(Role.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RoleResponse response = roleService.activate(id);

        assertEquals(true, response.active());

        verify(roleRepository).findById(id);
        verify(roleRepository).save(role);
    }

    @Test
    void shouldThrowExceptionWhenActivatingNonExistingRole() {

        UUID id = UUID.randomUUID();

        when(roleRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RoleNotFoundException.class,
                () -> roleService.activate(id)
        );

        verify(roleRepository).findById(id);

        verify(roleRepository, never())
                .save(any(Role.class));
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingNonExistingRole() {

        UUID id = UUID.randomUUID();

        when(roleRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                RoleNotFoundException.class,
                () -> roleService.desactivate(id)
        );

        verify(roleRepository).findById(id);

        verify(roleRepository, never())
                .save(any(Role.class));
    }

    @Test
    void shouldReturnAllRoles() {

        Role adminRole = new Role(
                "ADMIN",
                "Administrador",
                "Administrador general del sistema"
        );

        Role userRole = new Role(
                "USER",
                "Usuario",
                "Usuario general del sistema"
        );

        when(roleRepository.findAll())
                .thenReturn(List.of(adminRole, userRole));

        List<RoleResponse> responses = roleService.findAll();

        assertEquals(2, responses.size());

        assertEquals("ADMIN", responses.get(0).code());
        assertEquals("USER", responses.get(1).code());

        verify(roleRepository).findAll();
    }
}
