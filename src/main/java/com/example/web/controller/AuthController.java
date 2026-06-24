package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.constants.SessionAttributes;
import com.example.dto.LoginFormDTO;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping(Paths.LOGIN_PATH)
    public String loginPage(Model model) {
        model.addAttribute(RequestAttributes.LOGIN_FORM_DTO, new LoginFormDTO());
        return Pages.LOGIN;
    }

    @PostMapping(Paths.LOGIN_PATH)
    public String login(@ModelAttribute(RequestAttributes.LOGIN_FORM_DTO) LoginFormDTO loginFormDTO,
                        HttpSession session,
                        Model model) {
        String login = loginFormDTO.getLogin();
        String password = loginFormDTO.getPassword();
        String error = securityService.login(login, password);
        if (error != null) {
            model.addAttribute(RequestAttributes.ERROR_MESSAGE, error);
            return Pages.LOGIN;
        }
        session.setAttribute(SessionAttributes.USER, userService.getUserByLogin(login));
        return "redirect:" + Paths.WELCOME_PATH;
    }

    @PostMapping(Paths.LOGOUT_PATH)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:" + Paths.LOGIN_PATH;
    }
}
