package filter;

import constants.SessionAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of("/", "/index.jsp", "/login.jhtml");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();

        if (path.startsWith("/resources/")) {
            filterChain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute(SessionAttributes.USER) != null);
        boolean publicPath = PUBLIC_PATHS.contains(path);

        if (!loggedIn && !publicPath) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        if (loggedIn && publicPath) {
            resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
            return;
        }

        filterChain.doFilter(req, resp);
    }
}
