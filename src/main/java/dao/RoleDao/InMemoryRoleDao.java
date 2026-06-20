package dao.RoleDao;

import model.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRoleDao implements RoleDao {
    private static InMemoryRoleDao INSTANCE;

    private final Map<Integer, Role> roles = new HashMap<>();

    private final Map<Integer, Set<Integer>> userRoles = new HashMap<>();

    private InMemoryRoleDao() {
        roles.put(1, new Role(1, "Administrator"));
        roles.put(2, new Role(2, "Manager"));
        roles.put(3, new Role(3, "Bookkeeper"));
        roles.put(4, new Role(4, "Developer"));
        roles.put(5, new Role(5, "Designer"));

        userRoles.put(1, Set.of(2, 4));
        userRoles.put(2, Set.of(2));
        userRoles.put(3, Set.of(3));
        userRoles.put(4, Set.of(1, 2, 3, 4, 5));
        userRoles.put(5, Set.of(1));
    }

    static InMemoryRoleDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryRoleDao();
        }
        return INSTANCE;
    }

    @Override
    public Role findById(Integer id) {
        return roles.get(id);
    }

    @Override
    public Set<Role> findAll() {
        return Set.copyOf(roles.values());
    }

    @Override
    public Set<Role> findByUserId(Integer userId) {
        Set<Integer> roleIds = userRoles.get(userId);
        if (roleIds == null) {
            return Set.of();
        }
        return roleIds.stream().map(roles::get).collect(Collectors.toSet());
    }

    @Override
    public void saveRolesForUser(Integer userId, Set<Role> roles) {
        Set<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());

        userRoles.put(userId, roleIds);
    }

    @Override
    public void deleteRolesForUser(Integer userId) {
        userRoles.remove(userId);
    }
}
