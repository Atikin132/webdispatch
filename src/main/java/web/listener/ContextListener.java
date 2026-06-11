package web.listener;

import dao.UserDao;
import factory.UserDaoFactory;
import factory.UserDaoType;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = UserDaoFactory.getUserDao(UserDaoType.MEMORY);

        UserService.init(userDao);
        SecurityService.init(UserService.getInstance());
    }
}
