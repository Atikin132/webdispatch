package servlet;

import constants.Paths;
import constants.SessionAttributes;
import model.Role;
import model.User;
import service.SecurityService;

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

    private static final Set<String> ALLOWED_PAGES =
            Set.of("login", "welcome", "loginedit", "users", "useradd", "useredit");

    private final SecurityService securityService = new SecurityService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String pageName = path.substring(1, path.lastIndexOf("."));

        if (!ALLOWED_PAGES.contains(pageName)) {
            pageName = "unknown";
        }

        if (pageName.equals("users")) {
            req.setAttribute(SessionAttributes.USERS, securityService.getAllUsers());
        }

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
            case Paths.USER_ADD_PATH -> handleUserAdd(req, resp);
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
            sendError("Login and password cannot be empty", "login", req, resp);
            return;
        }

        if (securityService.login(login, password)) {
            User user = securityService.getUser(login);
            req.getSession().setAttribute(SessionAttributes.USER, user);
            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
        } else {
            sendError("Wrong login or password", "login", req, resp);
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

        forward("loginedit", req, resp);
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

    private void handleUserAdd(HttpServletRequest req,
                               HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(SessionAttributes.LOGIN);
        String password = req.getParameter(SessionAttributes.PASSWORD);
        String email = req.getParameter(SessionAttributes.EMAIL);
        String surname = req.getParameter(SessionAttributes.SURNAME);
        String name = req.getParameter(SessionAttributes.NAME);
        String patronymic = req.getParameter(SessionAttributes.PATRONYMIC);
        LocalDate birthday = LocalDate.parse(req.getParameter(SessionAttributes.BIRTHDAY));
        Role role = Role.valueOf(req.getParameter(SessionAttributes.ROLE));
        String errorMessage = validateUserForm(login,
                password,
                email,
                surname,
                name,
                patronymic,
                birthday.toString());

        if (errorMessage != null) {
            saveInputsAddForm(login, password, email, surname, name, patronymic, birthday, req);
            sendError(errorMessage, "useradd", req, resp);
            return;
        }
        if (securityService.existsByLogin(login)) {
            saveInputsAddForm(login, password, email, surname, name, patronymic, birthday, req);
            sendError("User with this login already exists", "useradd", req, resp);
            return;
        }
        if (!securityService.isBirthdayBeforeNow(birthday)) {
            saveInputsAddForm(login, password, email, surname, name, patronymic, birthday, req);
            sendError("The date must not be today or in the future", "useradd", req, resp);
            return;
        }

        User user = new User(login, password, email, surname, name, patronymic, birthday, role);
        securityService.addUser(user);
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

    private void saveInputsAddForm(String login,
                                   String password,
                                   String email,
                                   String surname,
                                   String name,
                                   String patronymic,
                                   LocalDate birthday,
                                   HttpServletRequest req) {
        req.setAttribute(SessionAttributes.LOGIN, login);
        req.setAttribute(SessionAttributes.PASSWORD, password);
        req.setAttribute(SessionAttributes.EMAIL, email);
        req.setAttribute(SessionAttributes.SURNAME, surname);
        req.setAttribute(SessionAttributes.NAME, name);
        req.setAttribute(SessionAttributes.PATRONYMIC, patronymic);
        req.setAttribute(SessionAttributes.BIRTHDAY, birthday);
    }
}
