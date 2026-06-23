package com.example.dao.RoleDao;

import com.example.dao.DatabaseConnection;
import com.example.model.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class DatabaseRoleDao implements RoleDao {

    @Override
    public Role findById(Integer id) {
        String sql = """
                SELECT id, name
                FROM "authorization".roles
                WHERE id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                        sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<Role> findAll() {
        Set<Role> roles = new HashSet<>();
        String sql = """
                SELECT id, name
                FROM "authorization".roles
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                        sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

    @Override
    public Set<Role> findByUserId(Integer userId) {
        Set<Role> roles = new HashSet<>();
        String sql = """
                SELECT
                    r.id,
                    r.name
                FROM "authorization".roles r
                JOIN "authorization".user_roles ur
                    ON r.id = ur.role_id
                WHERE ur.user_id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                        sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

    @Override
    public void saveRolesForUser(Connection con, Integer userId, Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return;
        }
        String sql = """
                INSERT INTO "authorization".user_roles
                (user_id, role_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (Role role : roles) {
                ps.setInt(1, userId);
                ps.setInt(2, role.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteRolesForUser(Connection con, Integer userId) {
        String sql = """
                DELETE FROM "authorization".user_roles
                WHERE user_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
