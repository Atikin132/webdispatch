package com.example.web.controller;

import com.example.dto.JWTResponse;
import com.example.dto.LoginDTO;
import com.example.model.CustomUserDetails;
import com.example.service.JWTService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getLogin(),
                        loginDTO.getPassword()));
        String token = jwtService.generateToken(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JWTResponse(token,
                customUserDetails.getId(),
                authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .toList()));
    }
}
