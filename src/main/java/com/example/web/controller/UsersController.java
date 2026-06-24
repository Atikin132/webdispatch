package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping(Paths.USERS_PATH)
    public String usersPage(Model model) {
        model.addAttribute(RequestAttributes.USERS, userService.getAllUsers());
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USERS);
        return Pages.USERS;
    }
}
