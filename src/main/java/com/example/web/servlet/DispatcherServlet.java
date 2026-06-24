package com.example.web.servlet;

import com.example.constants.*;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = "*.jhtml")
public class DispatcherServlet extends HttpServlet {

    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static final String JSP_EXTENSION = ".jsp";

    private static final Set<String> ALLOWED_PAGES = Set.of(Pages.LOGIN,
            Pages.WELCOME,
            Pages.LOGIN_EDIT,
            Pages.USERS,
            Pages.USER_ADD,
            Pages.USER_EDIT);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @Override
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String pageName = path.substring(1, path.lastIndexOf("."));

        if (!ALLOWED_PAGES.contains(pageName)) {
            pageName = Pages.UNKNOWN;
        }
        switch (pageName) {
            case Pages.USERS:
                req.setAttribute(RequestAttributes.USERS, userService.getAllUsers());
                break;
            case Pages.USER_ADD:
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "add");
                req.setAttribute("roles", roleService.findAll());
                req.setAttribute(RequestAttributes.USER, userService.createEmptyUser());
                req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
                pageName = Pages.USER_FORM;
                break;
            case Pages.USER_EDIT:
                Integer userId = Integer.parseInt(req.getParameter(RequestParams.ID));
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "edit");
                req.setAttribute("roles", roleService.findAll());
                req.setAttribute(RequestAttributes.USER, userService.getUser(userId));
                req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
                pageName = Pages.USER_FORM;
                break;
        }

        req.setAttribute(RequestAttributes.CURRENT_PAGE, pageName);
        forward(pageName, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
//            case Paths.LOGIN_PATH -> handleLogin(req, resp);
//            case Paths.LOGOUT_PATH -> handleLogout(req, resp);
//            case Paths.LOGIN_EDIT_PATH -> handlePasswordChange(req, resp);
            case Paths.USER_ADD_PATH -> handleUserForm(req, resp, false);
            case Paths.USER_EDIT_PATH -> handleUserForm(req, resp, true);
            case Paths.USER_DELETE_PATH -> handleUserDelete(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

//    private void handleLogin(HttpServletRequest req,
//                             HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter(RequestParams.LOGIN);
//        String password = req.getParameter(RequestParams.PASSWORD);
//
//        String loginAttempt = securityService.login(login, password);
//        if (loginAttempt == null) {
//            req.getSession()
//                    .setAttribute(SessionAttributes.USER, userService.getUserByLogin(login));
//            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
//        } else {
//            sendError(loginAttempt, Pages.LOGIN, req, resp);
//        }
//    }
//
//    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws
//    IOException {
//        HttpSession session = req.getSession();
//
//        if (session != null) {
//            session.invalidate();
//        }
//
//        resp.sendRedirect(req.getContextPath() + Paths.LOGIN_PATH);
//    }
//
//    private void handlePasswordChange(HttpServletRequest req,
//                                      HttpServletResponse resp) throws ServletException,
//            IOException {
//        HttpSession session = req.getSession(false);
//        User currentUser = (User) session.getAttribute(SessionAttributes.USER);
//        String oldPassword = req.getParameter(RequestParams.OLD_PASSWORD);
//        String newPassword = req.getParameter(RequestParams.NEW_PASSWORD);
//        boolean changePassword =
//                securityService.changePassword(currentUser.getId(), oldPassword, newPassword);
//
//        if (changePassword) {
//            req.setAttribute(RequestAttributes.SUCCESS_MESSAGE, "Password changed successfully");
//        } else {
//            req.setAttribute(RequestAttributes.ERROR_MESSAGE, "Old password is incorrect");
//        }
//        req.setAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);
//
//        forward(Pages.LOGIN_EDIT, req, resp);
//    }

    private void forward(String page,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_PATH + page + JSP_EXTENSION).forward(req, resp);
    }

    private void sendError(String errorText,
                           String forwardPage,
                           HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(RequestAttributes.ERROR_MESSAGE, errorText);
        forward(forwardPage, req, resp);
    }

    private void handleUserForm(HttpServletRequest req,
                                HttpServletResponse resp,
                                boolean isEdit) throws IOException, ServletException {
        String idStr = req.getParameter(RequestParams.ID);
        String login = req.getParameter(RequestParams.LOGIN);
        String password = req.getParameter(RequestParams.PASSWORD);
        String name = req.getParameter(RequestParams.NAME);
        String birthDateStr = req.getParameter(RequestParams.BIRTH_DATE);
        String ageStr = req.getParameter(RequestParams.AGE);
        String salaryStr = req.getParameter(RequestParams.SALARY);
        String[] selectedRoleIds = req.getParameterValues(RequestParams.ROLES);

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
            prepareUserForm(user, req);
            if (isEdit) {
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "edit");
            } else {
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "add");
            }
            sendError(error, Pages.USER_FORM, req, resp);
            return;
        }

        if (isEdit) {
            userService.updateUser(user);
        } else {
            userService.createUser(user);
        }

        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private void handleUserDelete(HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException {
        Integer userId = Integer.parseInt(req.getParameter(RequestParams.ID));
        userService.deleteUser(userId);
        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private void prepareUserForm(User user, HttpServletRequest req) {
        req.setAttribute(RequestAttributes.USER, user);
        req.setAttribute("roles", roleService.findAll());
        req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusYears(19));
    }
}
