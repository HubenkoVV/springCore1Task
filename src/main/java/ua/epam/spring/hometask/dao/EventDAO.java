package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import ua.epam.spring.hometask.domain.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class EventDAO {

    private JdbcTemplate template;

    public EventDAO(JdbcTemplate template) {
        this.template = template;
    }

    public Long addEvent(Event event) {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO event (name, basePrice, rating) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, event.getName());
                ps.setDouble(2, event.getBasePrice());
                ps.setString(3, String.valueOf(event.getRating()));
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    public void remove(Event event) {
        template.update("DELETE FROM event WHERE idevent = ?", event.getIdevent());
    }

    public Event getById(long id) {
        return template.queryForObject("SELECT * FROM event WHERE idevent = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Event.class));
    }

    public List getAll() {
        List<Event> events = template.query("SELECT * FROM event",
                new BeanPropertyRowMapper<>(Event.class));
        return events;
    }

    public Event getByName(String name) {
        return template.queryForObject("SELECT * FROM event WHERE name = ?",
                new Object[]{name},
                new BeanPropertyRowMapper<>(Event.class));
    }


    public Set<Event> getEventForDateRange(LocalDateTime from, LocalDateTime to) {
        List<Long> eventsId = template.query("SELECT event_id FROM seance WHERE " +
                        "dateTime <= ? AND dateTime >= ? GROUP BY event_id",
                new Object[]{to, from},
                new RowMapper<Long>() {
                    @Nullable
                    @Override
                    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Long.valueOf(resultSet.getInt("event_id"));
                    }
                });
        Set<Event> events = new HashSet<>();
        eventsId.forEach(id -> events.add(getById(id)));
        return events;
    }

    public void deleteAll() {
        template.update("delete FROM event");

    }
}
