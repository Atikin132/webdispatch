package dao.RoleDao;

public class RoleDaoFactory {


    private static final RoleDaoType DAO_TYPE = RoleDaoType.MEMORY;

    private RoleDaoFactory() {
    }

    public static RoleDao getUserDao() {
        return switch (DAO_TYPE) {
            case MEMORY -> InMemoryRoleDao.getInstance();
        };
    }
}
