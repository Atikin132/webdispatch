package web.filter;

import constants.Paths;
import constants.SessionAttributes;
import model.Role;
import model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of("/", "/index.jsp", "/login.jhtml");
    private static final Set<String> ADMINS_PATHS =
            Set.of("/users.jhtml", "/useradd.jhtml", "/useredit.jhtml", "/userdelete.jhtml");


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();

        if (path.startsWith("/resources/")) {
            filterChain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn =
                (session != null && session.getAttribute(SessionAttributes.USER) != null);
        boolean publicPath = PUBLIC_PATHS.contains(path);

        if (!loggedIn && !publicPath) {
            resp.sendRedirect(req.getContextPath() + Paths.LOGIN_PATH);
            return;
        }

        if (loggedIn && publicPath) {
            resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
            return;
        }

        if (loggedIn) {
            boolean adminsPath = ADMINS_PATHS.contains(path);
            if (adminsPath &&
                    !((User) session.getAttribute(SessionAttributes.USER)).hasRole("Administrator")) {
                resp.sendRedirect(req.getContextPath() + Paths.WELCOME_PATH);
                return;
            }
        }

        filterChain.doFilter(req, resp);
    }
}
