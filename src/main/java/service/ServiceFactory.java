package service;

import dao.RoleDao.RoleDaoFactory;
import dao.UserDao.UserDaoFactory;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService getUserService() {
        if (!UserService.isInitialized()) {
            UserService.init(UserDaoFactory.getUserDao(), RoleDaoFactory.getUserDao());
        }
        return UserService.getInstance();
    }

    public static SecurityService getSecurityService() {
        if (!SecurityService.isInitialized()) {
            SecurityService.init(getUserService());
        }
        return SecurityService.getInstance();
    }
}
