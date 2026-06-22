package service;

import dao.RoleDao.RoleDao;
import model.Role;

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


    Role findById(Integer id) {
        return roleDao.findById(id);
    }

    Set<Role> findAll() {
        return roleDao.findAll();
    }

    Set<Role> findByUserId(Integer userId) {
        return roleDao.findByUserId(userId);
    }

    void saveRolesForUser(Integer userId, Set<Role> roles) {
        roleDao.saveRolesForUser(userId, roles);
    }

    void deleteRolesForUser(Integer userId) {
        roleDao.deleteRolesForUser(userId);
    }
}
