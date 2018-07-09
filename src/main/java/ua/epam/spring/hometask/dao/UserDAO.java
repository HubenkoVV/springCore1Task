package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.domain.User;

import java.sql.*;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class UserDAO {

    private JdbcTemplate template;

    public UserDAO(JdbcTemplate template) {
        this.template = template;
    }

    public Long addUser(User user) {

        KeyHolder holder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO user (firstName, lastName, email, birthday) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setDate(4, Date.valueOf(user.getBirthday()));
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    public void remove(User user) {
        template.update("DELETE FROM user WHERE iduser = ?", user.getId());
    }

    public User getById(long id) {
        return (User) template.queryForObject("SELECT * FROM user WHERE iduser = ?",
                new Object[]{id},
                new BeanPropertyRowMapper(User.class));
    }

    public List getAll() {
        return template.query("SELECT * FROM user",
                new BeanPropertyRowMapper(User.class));
    }

    public User getByEmail(String name) {
        return (User) template.queryForObject("SELECT * FROM user WHERE email = ?",
                new Object[]{name},
                new BeanPropertyRowMapper(User.class));
    }
}
