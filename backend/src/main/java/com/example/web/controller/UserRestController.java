package com.example.web.controller;

import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserRequest;
import com.example.model.User;
import com.example.service.UserService;
import com.example.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        Collection<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(userMapper::toDTO).toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.fromRequest(userRequest);
        String error = userService.validateAndPrepareUser(user, userRequest.getRoles());
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id,
                                        @Valid @RequestBody UserRequest userRequest) {
        userRequest.setId(id);
        User user = userMapper.fromRequest(userRequest);
        String error = userService.validateAndPrepareUser(user, userRequest.getRoles());
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getLogin(), loginDTO.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error",
                    messageSource.getMessage("loginErrorDetail",
                            null,
                            LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok(userMapper.toDTO(user));
    }
}