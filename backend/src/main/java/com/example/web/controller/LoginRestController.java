package com.example.web.controller;

import com.example.dto.JWTResponse;
import com.example.dto.LoginDTO;
import com.example.model.User;
import com.example.service.JWTService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getLogin(), loginDTO.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid login or password"));
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(),
                null,
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList());
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(new JWTResponse(token,
                user.getId(),
                user.getLogin(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .toList()));
    }
}
