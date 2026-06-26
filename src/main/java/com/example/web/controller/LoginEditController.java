package com.example.web.controller;

import com.example.constants.Pages;
import com.example.constants.Paths;
import com.example.constants.RequestAttributes;
import com.example.constants.SessionAttributes;
import com.example.dto.PasswordChangeFormDTO;
import com.example.model.User;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginEditController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(Paths.LOGIN_EDIT_PATH)
    public String loginEditPage(Model model) {
        model.addAttribute(RequestAttributes.PASSWORD_CHANGE_FORM_DTO, new PasswordChangeFormDTO());
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
        return Pages.LOGIN_EDIT;
    }

    @PostMapping(Paths.LOGIN_EDIT_PATH)
    public String loginEdit(@Valid @ModelAttribute(RequestAttributes.PASSWORD_CHANGE_FORM_DTO) PasswordChangeFormDTO passwordChangeFormDTO,
                            BindingResult bindingResult,
                            HttpSession session,
                            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
            return Pages.LOGIN_EDIT;
        }
        User currentUser = (User) session.getAttribute(SessionAttributes.USER);
        boolean changePassword = securityService.changePassword(currentUser.getId(),
                passwordChangeFormDTO.getOldPassword(),
                passwordChangeFormDTO.getNewPassword());
        if (changePassword) {
            ;
            model.addAttribute(RequestAttributes.SUCCESS_MESSAGE,
                    messageSource.getMessage("passwordChangedSuccessfully",
                            null,
                            LocaleContextHolder.getLocale()));
        } else {
            model.addAttribute(RequestAttributes.ERROR_MESSAGE,
                    messageSource.getMessage("oldPasswordIncorrect",
                            null,
                            LocaleContextHolder.getLocale()));
        }
        model.addAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
        return Pages.LOGIN_EDIT;
    }
}
