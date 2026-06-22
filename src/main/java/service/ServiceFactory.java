package service;

import dao.RoleDao.RoleDaoFactory;
import dao.UserDao.UserDaoFactory;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService getUserService() {
        if (!UserService.isInitialized()) {
            UserService.init(UserDaoFactory.getUserDao(), getRoleService());
        }
        return UserService.getInstance();
    }

    public static RoleService getRoleService() {
        if (!RoleService.isInitialized()) {
            RoleService.init(RoleDaoFactory.getRoleDao());
        }
        return RoleService.getInstance();
    }

    public static SecurityService getSecurityService() {
        if (!SecurityService.isInitialized()) {
            SecurityService.init(getUserService());
        }
        return SecurityService.getInstance();
    }
}
