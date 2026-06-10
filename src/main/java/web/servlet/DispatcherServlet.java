package web.servlet;

import constants.Pages;
import constants.Paths;
import constants.SessionAttributes;
import dao.InMemoryUserDao;
import dao.UserDao;
import model.Role;
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

    private final UserDao userDao = new InMemoryUserDao();

    private final SecurityService securityService = new SecurityService(userDao);

    private final UserService userService = new UserService(userDao);

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
                req.setAttribute(SessionAttributes.USERS, userService.getAllUsers());
                break;
            case Pages.USER_ADD:
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "add");
                User newUser = new User();
                newUser.setRole(Role.USER);
                req.setAttribute(SessionAttributes.USER, newUser);
                req.setAttribute(SessionAttributes.MAX_DATE, LocalDate.now().minusDays(1));
                pageName = Pages.USER_FORM;
                break;
            case Pages.USER_EDIT:
                String login = req.getParameter(SessionAttributes.LOGIN);
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "edit");
                req.setAttribute(SessionAttributes.USER, userService.getUser(login));
                req.setAttribute(SessionAttributes.OLD_LOGIN, login);
                req.setAttribute(SessionAttributes.MAX_DATE, LocalDate.now().minusDays(1));
                pageName = Pages.USER_FORM;
                break;
        }

        req.setAttribute(SessionAttributes.CURRENT_PAGE, pageName);
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
        String login = req.getParameter(SessionAttributes.LOGIN);
        String password = req.getParameter(SessionAttributes.PASSWORD);
        login = (login != null) ? login.trim() : null;
        password = (password != null) ? password.trim() : null;

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            sendError("Login and password cannot be empty", Pages.LOGIN, req, resp);
            return;
        }

        if (securityService.login(login, password)) {
            User user = userService.getUser(login);
            req.getSession().setAttribute(SessionAttributes.USER, user);
            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
        } else {
            sendError("Wrong login or password", Pages.LOGIN, req, resp);
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
        String oldPassword = req.getParameter(SessionAttributes.OLD_PASSWORD);
        String newPassword = req.getParameter(SessionAttributes.NEW_PASSWORD);
        boolean changePassword =
                securityService.changePassword(currentUser.getLogin(), oldPassword, newPassword);

        if (changePassword) {
            currentUser.setPassword(newPassword);
            req.setAttribute(SessionAttributes.SUCCESS_MESSAGE, "Password changed successfully");
        } else {
            req.setAttribute(SessionAttributes.ERROR_MESSAGE, "Old password is incorrect");
        }
        req.setAttribute(SessionAttributes.CURRENT_PAGE, Pages.LOGIN_EDIT);

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
        req.setAttribute(SessionAttributes.ERROR_MESSAGE, errorText);
        forward(forwardPage, req, resp);
    }

    private void handleUserForm(HttpServletRequest req,
                                HttpServletResponse resp,
                                boolean isEdit) throws IOException, ServletException {
        String oldLogin = req.getParameter(SessionAttributes.OLD_LOGIN);

        String login = req.getParameter(SessionAttributes.LOGIN);
        String password = req.getParameter(SessionAttributes.PASSWORD);
        String email = req.getParameter(SessionAttributes.EMAIL);
        String surname = req.getParameter(SessionAttributes.SURNAME);
        String name = req.getParameter(SessionAttributes.NAME);
        String patronymic = req.getParameter(SessionAttributes.PATRONYMIC);
        String birthdayStr = req.getParameter(SessionAttributes.BIRTHDAY);
        String roleStr = req.getParameter(SessionAttributes.ROLE);
        String error =
                validateUserForm(login, password, email, surname, name, patronymic, birthdayStr);

        LocalDate birthday;
        try {
            birthday = LocalDate.parse(birthdayStr);
        } catch (Exception e) {
            prepareUserForm(new User(login, password, email, surname, name, patronymic, null, null),
                    req);
            sendError("Invalid birthday format", Pages.USER_FORM, req, resp);
            return;
        }

        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (Exception e) {
            prepareUserForm(new User(login, password, email, surname, name, patronymic, null, null),
                    req);
            sendError("Invalid role", Pages.USER_FORM, req, resp);
            return;
        }

        User user = new User(login, password, email, surname, name, patronymic, birthday, role);

        if (error != null) {
            prepareUserForm(user, req);
            if (isEdit) {
                req.setAttribute(SessionAttributes.OLD_LOGIN, oldLogin);
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "edit");
            } else {
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "add");
            }
            sendError(error, Pages.USER_FORM, req, resp);
            return;
        }
        if (!userService.isBirthdayBeforeNow(user.getBirthday())) {
            prepareUserForm(user, req);
            if (isEdit) {
                req.setAttribute(SessionAttributes.OLD_LOGIN, oldLogin);
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "edit");
            } else {
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "add");
            }
            sendError("The date must not be today or in the future", Pages.USER_FORM, req, resp);
            return;
        }
        if (isEdit) {
            if (userService.existsByLogin(user.getLogin()) && !oldLogin.equals(user.getLogin())) {
                prepareUserForm(user, req);
                req.setAttribute(SessionAttributes.OLD_LOGIN, oldLogin);
                sendError("User with this login already exists", Pages.USER_FORM, req, resp);
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "edit");
                return;
            }
            userService.updateUser(oldLogin, user);
        } else {
            if (userService.existsByLogin(user.getLogin())) {
                prepareUserForm(user, req);
                sendError("User with this login already exists", Pages.USER_FORM, req, resp);
                req.setAttribute(SessionAttributes.USER_FORM_MODE, "add");
                return;
            }

            userService.createUser(user);
        }

        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private void handleUserDelete(HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException {
        String login = req.getParameter(SessionAttributes.LOGIN);
        userService.deleteUser(login);
        resp.sendRedirect(req.getContextPath() + Paths.USERS_PATH);
    }

    private String validateUserForm(String login,
                                    String password,
                                    String email,
                                    String surname,
                                    String name,
                                    String patronymic,
                                    String birthday) {
        if (login == null || login.trim().isEmpty()) {
            return "Login is required";
        }
        if (password == null || password.length() < 6) {
            return "Password must contain at least 6 characters";
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Invalid email";
        }
        if (surname == null || surname.trim().isEmpty()) {
            return "Surname is required";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Name is required";
        }
        if (patronymic == null || patronymic.trim().isEmpty()) {
            return "Patronymic is required";
        }
        if (birthday == null || birthday.isBlank()) {
            return "Birthday is required";
        }
        return null;
    }

    private void prepareUserForm(User user, HttpServletRequest req) {
        req.setAttribute(SessionAttributes.USER, user);
        req.setAttribute(SessionAttributes.MAX_DATE, LocalDate.now().minusDays(1));
    }
}
