package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.dto.UserFormDTO;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(Paths.USERS_PATH)
    public String usersPage(Model model) {
        Collection<User> users = userService.getAllUsers();
        for (User user : users) {
            for (Role role : user.getRoles()) {
                role.setDisplayName(messageSource.getMessage("role" + role.getName(),
                        null,
                        LocaleContextHolder.getLocale()));
            }
        }
        model.addAttribute(RequestAttributes.USERS, users);
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USERS);
        return Pages.USERS;
    }

    @GetMapping(Paths.USER_ADD_PATH)
    public String userAddPage(Model model) {
        model.addAttribute(RequestAttributes.USER_FORM_MODE, "add");
        model.addAttribute(RequestAttributes.USER_FORM_DTO, toDto(userService.createEmptyUser()));
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USER_FORM);
        return Pages.USER_FORM;
    }

    @GetMapping(Paths.USER_EDIT_PATH)
    public String userEditPage(@RequestParam("id") Integer userId, Model model) {
        model.addAttribute(RequestAttributes.USER_FORM_MODE, "edit");
        model.addAttribute(RequestAttributes.USER_FORM_DTO, toDto(userService.getUser(userId)));
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.USER_FORM);
        return Pages.USER_FORM;
    }

    @PostMapping(Paths.USER_ADD_PATH)
    public String userAdd(@Valid @ModelAttribute(RequestAttributes.USER_FORM_DTO) UserFormDTO userFormDTO,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(RequestAttributes.USER_FORM_MODE, "add");
            return Pages.USER_FORM;
        }
        return handleUserForm(userFormDTO, model, false);
    }

    @PostMapping(Paths.USER_EDIT_PATH)
    public String userEdit(@Valid @ModelAttribute(RequestAttributes.USER_FORM_DTO) UserFormDTO userFormDTO,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(RequestAttributes.USER_FORM_MODE, "edit");
            return Pages.USER_FORM;
        }
        return handleUserForm(userFormDTO, model, true);
    }

    @PostMapping(Paths.USER_DELETE_PATH)
    public String userDelete(@RequestParam("id") Integer userId) {
        userService.deleteUser(userId);
        return "redirect:" + Paths.USERS_PATH;
    }

    @ModelAttribute(RequestAttributes.ROLES)
    public Set<Role> roles() {
        Set<Role> roles = roleService.findAll();
        for (Role role : roles) {
            role.setDisplayName(messageSource.getMessage("role" + role.getName(),
                    null,
                    LocaleContextHolder.getLocale()));
        }
        return roles;
    }

    @ModelAttribute(RequestAttributes.MAX_DATE)
    public LocalDate maxDate() {
        return LocalDate.now().minusYears(19);
    }

    private String handleUserForm(UserFormDTO userFormDTO, Model model, boolean isEdit) {
        User user = new User(userFormDTO.getId(),
                userFormDTO.getLogin().trim(),
                userFormDTO.getPassword().trim(),
                userFormDTO.getName().trim(),
                userFormDTO.getBirthDate(),
                userFormDTO.getAge(),
                userFormDTO.getSalary(),
                new HashSet<>());

        String error = userService.validateAndPrepareUser(user, userFormDTO.getRoles());

        if (error != null) {
            model.addAttribute(RequestAttributes.USER_FORM_MODE, isEdit ? "edit" : "add");
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

    private UserFormDTO toDto(User user) {
        UserFormDTO dto = new UserFormDTO();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setBirthDate(user.getBirthDate());
        dto.setAge(user.getAge());
        dto.setSalary(user.getSalary());

        String[] roles = new String[user.getRoles().size()];
        int i = 0;
        for (Role role : user.getRoles()) {
            roles[i++] = String.valueOf(role.getId());
        }
        dto.setRoles(roles);

        return dto;
    }
}
