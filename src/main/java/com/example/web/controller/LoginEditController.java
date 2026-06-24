package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.constants.SessionAttributes;
import com.example.model.User;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginEditController {

    @Autowired
    private SecurityService securityService;

    @GetMapping(Paths.LOGIN_EDIT_PATH)
    public String loginEditPage(Model model) {
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
        return Pages.LOGIN_EDIT;
    }

    @PostMapping(Paths.LOGIN_EDIT_PATH)
    public String loginEdit(@RequestParam String oldPassword,
                            @RequestParam String newPassword,
                            HttpSession session,
                            Model model) {
        User currentUser = (User) session.getAttribute(SessionAttributes.USER);
        boolean changePassword =
                securityService.changePassword(currentUser.getId(), oldPassword, newPassword);
        if (changePassword) {
            model.addAttribute(RequestAttributes.SUCCESS_MESSAGE, "Password changed successfully");
        } else {
            model.addAttribute(RequestAttributes.ERROR_MESSAGE, "Old password is incorrect");
        }
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
        return Pages.LOGIN_EDIT;
    }
}
