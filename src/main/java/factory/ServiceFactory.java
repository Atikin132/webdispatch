package factory;

import service.SecurityService;
import service.UserService;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService getUserService() {
        if (!UserService.isInitialized()) {
            UserService.init(UserDaoFactory.getUserDao());
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
