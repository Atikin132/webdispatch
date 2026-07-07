package com.example.web.controller;

import com.example.dto.RoleDTO;
import com.example.service.RoleService;
import com.example.utils.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RolesRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<Set<RoleDTO>> getAllRoles() {
        Set<RoleDTO> roles =
                roleService.findAll().stream().map(roleMapper::toDTO).collect(Collectors.toSet());
        return ResponseEntity.ok(roles);
    }
}
