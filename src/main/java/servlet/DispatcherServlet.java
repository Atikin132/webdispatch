package servlet;

import constants.Paths;
import constants.SessionAttributes;
import model.User;
import service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req,
                             HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        login = (login != null) ? login.trim() : null;
        password = (password != null) ? password.trim() : null;

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("errorMessage", "Login and password cannot be empty");
            forward("login", req, resp);
            return;
        }

        if (securityService.login(login, password)) {
            User user = securityService.getUser(login);
            req.getSession().setAttribute(SessionAttributes.USER, user);
            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
        } else {
            req.setAttribute("errorMessage", "Wrong login or password");
            forward("login", req, resp);
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
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        boolean changePassword =
                securityService.changePassword(currentUser.getLogin(), oldPassword, newPassword);

        if (changePassword) {
            currentUser.setPassword(newPassword);
            req.setAttribute("successMessage", "Password changed successfully");
        } else {
            req.setAttribute("errorMessage", "Old password is incorrect");
        }

        forward("loginedit", req, resp);
    }

    private void forward(String page,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_PATH + page + JSP_EXTENSION).forward(req, resp);
    }

}
