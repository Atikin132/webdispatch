package servlet;

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

    private static final String LOGIN_PATH = "/login.jhtml";
    private static final String LOGOUT_PATH = "/logout.jhtml";
    private static final String WELCOME_PATH = "/welcome.jhtml";

    private static final String USER_ATTR = "user";
    private static final Set<String> ALLOWED_PAGES = Set.of("login", "welcome", "loginedit");

    private final SecurityService securityService = new SecurityService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String pageName = path.substring(1, path.lastIndexOf("."));

        if (!ALLOWED_PAGES.contains(pageName)) {
            pageName = "unknown";
        }

        forward(pageName, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case LOGIN_PATH -> handleLogin(req, resp);
            case LOGOUT_PATH -> handleLogout(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (securityService.login(login, password)) {
            User user = securityService.getUser(login);
            req.getSession().setAttribute(USER_ATTR, user);
            System.out.println(req.getSession().getAttribute(USER_ATTR));
            resp.sendRedirect(req.getContextPath() + WELCOME_PATH);
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

        resp.sendRedirect(req.getContextPath() + LOGIN_PATH);
    }

    private void forward(String page, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_PATH + page + JSP_EXTENSION).forward(req, resp);
    }

}
