package dao.UserDao;

public class UserDaoFactory {

    private static final UserDaoType DAO_TYPE = UserDaoType.MEMORY;

    private UserDaoFactory() {
    }

    public static UserDao getUserDao() {
        return switch (DAO_TYPE) {
            case MEMORY -> InMemoryUserDao.getInstance();
        };
    }
}
