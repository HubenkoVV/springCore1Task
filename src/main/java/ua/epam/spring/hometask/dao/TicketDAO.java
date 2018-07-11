package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.domain.Ticket;

import java.sql.*;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/11/2018.
 */
public class TicketDAO {

    private JdbcTemplate template;

    public TicketDAO(JdbcTemplate template) {
        this.template = template;
    }

    public Long addTicket(Ticket ticket) {

        KeyHolder holder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO ticket (user, seance, seat) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                if (ticket.getUser() != null)
                    ps.setLong(1, ticket.getUser());
                else ps.setNull(1, Types.INTEGER);
                ps.setLong(2, ticket.getSeance());
                ps.setLong(3, ticket.getSeat());
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    public void deleteAll() {
        template.update("delete FROM ticket");
    }

    public List<Ticket> getAll() {
        return template.query("SELECT * FROM ticket",
                new BeanPropertyRowMapper<>(Ticket.class));
    }

    public List<Ticket> getForSeance(Long id) {
        return template.query("SELECT * FROM ticket WHERE seance = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Ticket.class));
    }

    public List<Ticket> getForUser(Long id) {
        return template.query("SELECT * FROM ticket WHERE user = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Ticket.class));
    }

    public List<Ticket> getForUserBySeance(Long idUser, Long idSeance) {
        return template.query("SELECT * FROM ticket WHERE user = ? AND seance = ?",
                new Object[]{idUser, idSeance},
                new BeanPropertyRowMapper<>(Ticket.class));
    }

    public List<Ticket> getForEvent(Long id) {
        return template.query("SELECT * FROM ticket left join seance ON seance = idseance WHERE event_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Ticket.class));
    }
}
