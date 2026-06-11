package web.servlet;

import constants.*;
import factory.ServiceFactory;
import model.User;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class DispatcherServlet extends HttpServlet {

    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static final String JSP_EXTENSION = ".jsp";

    private static final Set<String> ALLOWED_PAGES = Set.of(Pages.LOGIN,
            Pages.WELCOME,
            Pages.LOGIN_EDIT,
            Pages.USERS,
            Pages.USER_ADD,
            Pages.USER_EDIT);

    private SecurityService securityService;
    private UserService userService;

    @Override
    public void init() {
        this.userService = ServiceFactory.getUserService();
        this.securityService =  ServiceFactory.getSecurityService();
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
                req.setAttribute(RequestAttributes.USER, userService.createEmptyUser());
                req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusDays(1));
                pageName = Pages.USER_FORM;
                break;
            case Pages.USER_EDIT:
                String login = req.getParameter(RequestParams.LOGIN);
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "edit");
                req.setAttribute(RequestAttributes.USER, userService.getUser(login));
                req.setAttribute(RequestAttributes.OLD_LOGIN, login);
                req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusDays(1));
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
            case Paths.LOGIN_PATH -> handleLogin(req, resp);
            case Paths.LOGOUT_PATH -> handleLogout(req, resp);
            case Paths.LOGIN_EDIT_PATH -> handlePasswordChange(req, resp);
            case Paths.USER_ADD_PATH -> handleUserForm(req, resp, false);
            case Paths.USER_EDIT_PATH -> handleUserForm(req, resp, true);
            case Paths.USER_DELETE_PATH -> handleUserDelete(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req,
                             HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(RequestParams.LOGIN);
        String password = req.getParameter(RequestParams.PASSWORD);

        String loginAttempt = securityService.login(login, password);
        if (loginAttempt == null) {
            req.getSession().setAttribute(SessionAttributes.USER, userService.getUser(login));
            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
        } else {
            sendError(loginAttempt, Pages.LOGIN, req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (session != null) {
            session.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + Paths.LOGIN_PATH);
    }

    private void handlePasswordChange(HttpServletRequest req,
                                      HttpServletResponse resp) throws ServletException,
            IOException {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute(SessionAttributes.USER);
        String oldPassword = req.getParameter(RequestParams.OLD_PASSWORD);
        String newPassword = req.getParameter(RequestParams.NEW_PASSWORD);
        boolean changePassword =
                securityService.changePassword(currentUser.getLogin(), oldPassword, newPassword);

        if (changePassword) {
            req.setAttribute(RequestAttributes.SUCCESS_MESSAGE, "Password changed successfully");
        } else {
            req.setAttribute(RequestAttributes.ERROR_MESSAGE, "Old password is incorrect");
        }
        req.setAttribute(RequestAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);

        forward(Pages.LOGIN_EDIT, req, resp);
    }

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
        String oldLogin = req.getParameter(RequestParams.OLD_LOGIN);

        String login = req.getParameter(RequestParams.LOGIN);
        String password = req.getParameter(RequestParams.PASSWORD);
        String email = req.getParameter(RequestParams.EMAIL);
        String surname = req.getParameter(RequestParams.SURNAME);
        String name = req.getParameter(RequestParams.NAME);
        String patronymic = req.getParameter(RequestParams.PATRONYMIC);
        String birthdayStr = req.getParameter(RequestParams.BIRTHDAY);
        String roleStr = req.getParameter(RequestParams.ROLE);

        User user = new User(login, password, email, surname, name, patronymic, null, null);

        String error = isEdit ? userService.validateAndPrepareUser(user,
                birthdayStr,
                roleStr,
                oldLogin) : userService.validateAndPrepareUser(user, birthdayStr, roleStr, null);

        if (error != null) {
            prepareUserForm(user, req);
            if (isEdit) {
                req.setAttribute(RequestAttributes.OLD_LOGIN, oldLogin);
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "edit");
            } else {
                req.setAttribute(RequestAttributes.USER_FORM_MODE, "add");
            }
            sendError(error, Pages.USER_FORM, req, resp);
            return;
        }

        if (isEdit) {
            userService.updateUser(oldLogin, user);
        } else {
            userService.createUser(user);
        }

        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private void handleUserDelete(HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException {
        String login = req.getParameter(RequestParams.LOGIN);
        userService.deleteUser(login);
        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private void prepareUserForm(User user, HttpServletRequest req) {
        req.setAttribute(RequestAttributes.USER, user);
        req.setAttribute(RequestAttributes.MAX_DATE, LocalDate.now().minusDays(1));
    }
}
