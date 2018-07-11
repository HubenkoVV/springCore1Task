package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seance;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public class SeanceDAO {

    private JdbcTemplate template;

    public SeanceDAO(JdbcTemplate template) {
        this.template = template;
    }

    public void deleteAll() {
        template.update("delete FROM seance");
    }

    public Seance addSeance(Seance seance) {
        long millis = seance.getDateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        Date date = new Date(millis);
        template.update("INSERT INTO seance (event_id, auditorium_id, dateTime) VALUES (?,?,?)",
                seance.getEvent(), seance.getAuditorium(), date);
        return seance;
    }

    public List getAll() {
        return template.query("SELECT * FROM seance",
                new BeanPropertyRowMapper<>(Seance.class));
    }

    public List getAllForEvent(Long id) {
        return template.query("SELECT * FROM seance WHERE event_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Seance.class));
    }


    public List<LocalDateTime> getDatesForEvent(Long id) {
        return template.query("SELECT dateTime FROM seance WHERE event_id = ?",
                new Object[]{id},
                new RowMapper<LocalDateTime>() {
                    @Nullable
                    @Override
                    public LocalDateTime mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new java.sql.Timestamp(
                                resultSet.getDate("dateTime").getTime()).toLocalDateTime();
                    }
                });
    }

    public Long getAuditorimForEventByDate(Long id, LocalDateTime dateTime) {
        long millis = dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        Date date = new Date(millis);
        return template.queryForObject("SELECT auditorium_id FROM seance WHERE event_id = ? AND dateTime = ?",
                new Object[]{id, date},
                new RowMapper<Long>() {
                    @Nullable
                    @Override
                    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getLong("auditorium_id");
                    }
                });
    }

    public Seance getByEventAndDate(Event event, LocalDateTime dateTime) {
        long millis = dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        Date date = new Date(millis);
        return template.queryForObject("SELECT * FROM seance WHERE event_id = ? AND dateTime = ?",
                new Object[]{event.getIdevent(), date},
                new BeanPropertyRowMapper<>(Seance.class));
    }
}
