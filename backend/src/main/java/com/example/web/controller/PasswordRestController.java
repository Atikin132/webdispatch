package com.example.web.controller;

import com.example.dto.PasswordChangeFormDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/password/{id}")
public class PasswordRestController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> changePassword(@PathVariable("id") Integer userId,
                                            @Valid @RequestBody PasswordChangeFormDTO passwordChangeFormDTO) {
        String oldPassword = passwordChangeFormDTO.getOldPassword() ==
                null ? null : passwordChangeFormDTO.getOldPassword().trim();
        String newPassword = passwordChangeFormDTO.getNewPassword() ==
                null ? null : passwordChangeFormDTO.getNewPassword().trim();
        boolean isChanged = userService.changePassword(userId, oldPassword, newPassword);
        if (isChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }
    }
}