package service;

import dao.RoleDao.RoleDao;
import model.Role;

import java.sql.Connection;
import java.util.Set;

public class RoleService {
    private static RoleService INSTANCE;
    private final RoleDao roleDao;

    private RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    static RoleService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("RoleService is not initialized");
        }
        return INSTANCE;
    }

    static void init(RoleDao roleDao) {
        if (INSTANCE == null) {
            INSTANCE = new RoleService(roleDao);
        }
    }

    static boolean isInitialized() {
        return INSTANCE != null;
    }


    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    public Set<Role> findAll() {
        return roleDao.findAll();
    }

    public Set<Role> findByUserId(Integer userId) {
        return roleDao.findByUserId(userId);
    }

    public void saveRolesForUser(Integer userId, Set<Role> roles) {
        roleDao.saveRolesForUser(userId, roles);
    }

    public void saveRolesForUser(Connection con, Integer userId, Set<Role> roles) {
        roleDao.saveRolesForUser(con, userId, roles);
    }

    public void deleteRolesForUser(Integer userId) {
        roleDao.deleteRolesForUser(userId);
    }

    public void deleteRolesForUser(Connection con, Integer userId) {
        roleDao.deleteRolesForUser(con, userId);
    }
}
