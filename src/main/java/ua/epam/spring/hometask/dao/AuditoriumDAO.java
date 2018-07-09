package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import ua.epam.spring.hometask.domain.Auditorium;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class AuditoriumDAO {

    private JdbcTemplate template;

    public AuditoriumDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List getAll() {
        return template.query("SELECT * FROM auditorium",
                new BeanPropertyRowMapper(Auditorium.class));
    }

    public Auditorium getByName(String name) {
        return (Auditorium) template.queryForObject("SELECT * FROM auditorium WHERE name = ?",
                new Object[]{name},
                new BeanPropertyRowMapper(Auditorium.class));
    }

    public Long addAuditorium(Auditorium auditorium) {

        KeyHolder holder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO auditorium (name, numberOfSeats) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, auditorium.getName());
                ps.setInt(2, (int) auditorium.getNumberOfSeats());
                return ps;
            }
        }, holder);

        Long id = holder.getKey().longValue();

        for (Long seatNum : auditorium.getVipSeats()) {
            template.update("INSERT INTO seat_vip (number, auditor) VALUES (?,?)",
                    seatNum, id);
        }

        return id;
    }

    private Set<Long> seatsVIP(long id) {
        List<Long> seats = template.query("SELECT * FROM seat_vip WHERE auditor = ?",
                new Object[]{id},
                new RowMapper<Long>() {
                    @Nullable
                    @Override
                    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Long.valueOf(resultSet.getInt("number"));
                    }
                });
        return new HashSet<>(seats);
    }

    /*private Auditorium getAuditorium(ResultSet resultSet) throws SQLException {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(Long.valueOf(resultSet.getInt("id")));
        auditorium.setName(resultSet.getString("name"));
        auditorium.setNumberOfSeats(resultSet.getInt("numberOfSeats"));
        auditorium.setVipSeats(seatsVIP(auditorium.getId()));
        return auditorium;
    }*/


}
