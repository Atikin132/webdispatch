package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping(Paths.LOGIN_PATH)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute(RequestAttributes.ERROR_MESSAGE,
                    messageSource.getMessage("securityLoginError",
                            null,
                            LocaleContextHolder.getLocale()));
        }
        return Pages.LOGIN;
    }
}
