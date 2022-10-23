package telros.task.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import telros.task.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UserDbRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users (lastname, name, patronymic, birthday, email, phone_number) " +
                "values (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getLastname());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPatronymic());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhoneNumber());
            return stmt;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().longValue());
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users " +
                "set lastname =?, name =?, patronymic =?, birthday =?, email =?, phone_number =?" +
                "where id =?";
        jdbcTemplate.update(sql,
                user.getLastname(),
                user.getName(),
                user.getPatronymic(),
                user.getBirthday(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getId());
        return user;
    }

    @Override
    public User getUser(long userId) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId).get(0);
    }

    @Override
    public boolean removeUser(long userId) {
        String sql = "delete from users where id = ?";
        return jdbcTemplate.update(sql, userId) > 0;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String lastname = rs.getString("lastname");
        String name = rs.getString("name");
        String patronymic = rs.getString("patronymic");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone_number");
        return new User(id, lastname, name, patronymic, birthday, email, phoneNumber);
    }
}
