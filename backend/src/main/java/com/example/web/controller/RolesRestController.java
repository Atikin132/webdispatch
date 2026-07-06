package com.example.web.controller;

import com.example.model.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RolesRestController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Set<Role>> getAllRoles() {
        Set<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }
}
