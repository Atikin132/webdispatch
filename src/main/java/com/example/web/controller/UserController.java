package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(Paths.USERS_PATH)
    public String usersPage(Model model) {
        model.addAttribute(RequestAttributes.USERS, userService.getAllUsers());
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USERS);
        return Pages.USERS;
    }

    @GetMapping(Paths.USER_ADD_PATH)
    public String userAddPage(Model model) {
        model.addAttribute(RequestAttributes.USER_FORM_MODE, "add");
        model.addAttribute(RequestAttributes.ROLES, roleService.findAll());
        model.addAttribute(RequestAttributes.USER, userService.createEmptyUser());
        model.addAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USER_FORM);
        return Pages.USER_FORM;
    }

    @GetMapping(Paths.USER_EDIT_PATH)
    public String userEditPage(@RequestParam("id") Integer userId, Model model) {
        model.addAttribute(RequestAttributes.USER_FORM_MODE, "edit");
        model.addAttribute(RequestAttributes.ROLES, roleService.findAll());
        model.addAttribute(RequestAttributes.USER, userService.getUser(userId));
        model.addAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USER_FORM);
        return Pages.USER_FORM;
    }

    @GetMapping(Paths.USER_DELETE_PATH)
    public String userDeletePage(Model model) {
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USERS);
        return Pages.USERS;
    }

    @PostMapping(Paths.USER_ADD_PATH)
    public String userAdd(@RequestParam("id") String idStr,
                          @RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam("birthDate") String birthDateStr,
                          @RequestParam("age") String ageStr,
                          @RequestParam("salary") String salaryStr,
                          @RequestParam(value = "roles", required = false) String[] selectedRoleIds,
                          Model model) {
        return handleUserForm(idStr,
                login,
                password,
                name,
                birthDateStr,
                ageStr,
                salaryStr,
                selectedRoleIds,
                model,
                false);
    }

    @PostMapping(Paths.USER_EDIT_PATH)
    public String userEdit(@RequestParam("id") String idStr,
                           @RequestParam String login,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam("birthDate") String birthDateStr,
                           @RequestParam("age") String ageStr,
                           @RequestParam("salary") String salaryStr,
                           @RequestParam(value = "roles", required = false) String[] selectedRoleIds,
                           Model model) {
        return handleUserForm(idStr,
                login,
                password,
                name,
                birthDateStr,
                ageStr,
                salaryStr,
                selectedRoleIds,
                model,
                true);
    }

    @PostMapping(Paths.USER_DELETE_PATH)
    public String userDelete(@RequestParam("id") Integer userId) {
        userService.deleteUser(userId);
        return "redirect:" + Paths.USERS_PATH;
    }

    private String handleUserForm(String idStr,
                                  String login,
                                  String password,
                                  String name,
                                  String birthDateStr,
                                  String ageStr,
                                  String salaryStr,
                                  String[] selectedRoleIds,
                                  Model model,
                                  boolean isEdit) {
        User user = new User(null,
                login.trim(),
                password.trim(),
                name.trim(),
                null,
                null,
                null,
                new HashSet<>());

        String error = userService.validateAndPrepareUser(user,
                idStr,
                birthDateStr,
                ageStr,
                salaryStr,
                selectedRoleIds);

        if (error != null) {
            model.addAttribute(RequestAttributes.USER, user);
            model.addAttribute(RequestAttributes.ROLES, roleService.findAll());
            model.addAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
            if (isEdit) {
                model.addAttribute(RequestAttributes.USER_FORM_MODE, "edit");
            } else {
                model.addAttribute(RequestAttributes.USER_FORM_MODE, "add");
            }
            model.addAttribute(RequestAttributes.ERROR_MESSAGE, error);
            return Pages.USER_FORM;
        }

        if (isEdit) {
            userService.updateUser(user);
        } else {
            userService.createUser(user);
        }

        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USERS);
        return "redirect:" + Paths.USERS_PATH;
    }
}
