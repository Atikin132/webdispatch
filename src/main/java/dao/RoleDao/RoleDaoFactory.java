package dao.RoleDao;

public class RoleDaoFactory {


    private static final RoleDaoType DAO_TYPE = RoleDaoType.DATABASE;

    private RoleDaoFactory() {
    }

    public static RoleDao getRoleDao() {
        return switch (DAO_TYPE) {
            case MEMORY -> InMemoryRoleDao.getInstance();
            case DATABASE -> DatabaseRoleDao.getInstance();
        };
    }
}
