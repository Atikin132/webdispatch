package com.example.web.controller;

import com.example.dto.PasswordChangeFormDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/password/{id}")
public class PasswordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @PutMapping
    public ResponseEntity<?> changePassword(@PathVariable("id") Integer userId,
                                            @Valid @RequestBody PasswordChangeFormDTO passwordChangeFormDTO) {
        String oldPassword = passwordChangeFormDTO.getOldPassword() ==
                null ? null : passwordChangeFormDTO.getOldPassword().trim();
        String newPassword = passwordChangeFormDTO.getNewPassword() ==
                null ? null : passwordChangeFormDTO.getNewPassword().trim();
        boolean isChanged = userService.changePassword(userId, oldPassword, newPassword);
        if (isChanged) {
            String successMsg = messageSource.getMessage("passwordChangedSuccessfully",
                    null,
                    LocaleContextHolder.getLocale());
            return ResponseEntity.ok(Map.of("message", successMsg));
        } else {
            String errorMsg = messageSource.getMessage("oldPasswordIncorrect",
                    null,
                    LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(Map.of("error", errorMsg));
        }
    }
}