package web.listener;

import dao.InMemoryUserDao;
import dao.UserDao;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new InMemoryUserDao();

        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userDao);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("securityService", securityService);
    }
}
