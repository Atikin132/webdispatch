package dao.UserDao;

import dao.DatabaseConnection;
import model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DatabaseUserDao implements UserDao {

    private static DatabaseUserDao INSTANCE;

    private DatabaseUserDao() {
    }

    static DatabaseUserDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseUserDao();
        }
        return INSTANCE;
    }

    @Override
    public void create(User user) {
        String sql = """
                INSERT INTO "authorization".users
                (login,password,name,birth_date,age,salary)
                VALUES (?,?,?,?,?,?)
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getBirthday());
            ps.setInt(5, user.getAge());
            if (user.getSalary() == null) {
                ps.setBigDecimal(6, BigDecimal.valueOf(1000));
            } else {
                ps.setBigDecimal(6, user.getSalary());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Integer id, User user) {
        String sql = """
                UPDATE "authorization".users
                SET login=?,
                password=?,
                name=?,
                birth_date=?,
                age=?,
                salary=?
                WHERE id=?
                """;
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getBirthday());
            ps.setInt(5, user.getAge());
            if (user.getSalary() == null) {
                ps.setBigDecimal(6, BigDecimal.valueOf(1000));
            } else {
                ps.setBigDecimal(6, user.getSalary());
            }
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User read(Integer id) {
        String sql = """
                SELECT *
                       FROM "authorization".users
                       WHERE id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement("""
                    DELETE FROM "authorization".user_roles
                    WHERE user_id=?
                    """); PreparedStatement ps2 = con.prepareStatement("""
                    DELETE FROM "authorization".users
                    WHERE id=?
                    """)) {
                ps1.setInt(1, id);
                ps1.executeUpdate();

                ps2.setInt(1, id);
                ps2.executeUpdate();
                con.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = """
                SELECT *
                FROM "authorization".users
                ORDER BY id
                """;
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void updatePassword(Integer id, String newPassword) {
        String sql = """
                UPDATE "authorization".users
                SET password = ?
                WHERE id = ?
                """;
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        String sql = """
                SELECT *
                FROM "authorization".users
                WHERE login = ?
                """;

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps =
                con.prepareStatement(
                sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getObject("birth_date", LocalDate.class),
                rs.getInt("age"),
                rs.getBigDecimal("salary"),
                new HashSet<>());

    }
}
