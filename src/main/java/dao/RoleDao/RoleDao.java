package dao.RoleDao;

import model.Role;

import java.util.Set;

public interface RoleDao {
    Role findById(Integer id);

    Set<Role> findAll();

    Set<Role> findByUserId(Integer userId);

    void saveRolesForUser(Integer userId, Set<Role> roles);

    void deleteRolesForUser(Integer userId);
}
