package factory;

import dao.InMemoryUserDao;
import dao.UserDao;

public class UserDaoFactory {

    private UserDaoFactory() {
    }

    public static UserDao getUserDao(UserDaoType type) {
        return switch (type) {
            case MEMORY -> InMemoryUserDao.getInstance();
        };
    }
}
