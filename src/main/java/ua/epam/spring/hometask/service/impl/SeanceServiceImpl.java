package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.SeanceDAO;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seance;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.SeanceService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public class SeanceServiceImpl implements SeanceService {

    private SeanceDAO seanceDAO;
    private TicketDAO ticketDAO;

    public SeanceServiceImpl(SeanceDAO seanceDAO, TicketDAO ticketDAO) {
        this.seanceDAO = seanceDAO;
        this.ticketDAO = ticketDAO;
    }

    @Override
    public Seance save(@Nonnull Seance object) {
        return seanceDAO.addSeance(object);
    }

    @Nonnull
    @Override
    public List getAll() {
        return seanceDAO.getAll();
    }

    @Override
    public List<LocalDateTime> getDatesForEvent(Long id) {
        return seanceDAO.getDatesForEvent(id);
    }

    @Override
    public Long getAuditorimForEventByDate(Long id, LocalDateTime date) {
        return seanceDAO.getAuditorimForEventByDate(id, date);
    }

    @Override
    public List<Ticket> getTickets(Seance seance) {
        return ticketDAO.getForSeance(seance.getIdseance());
    }

    @Override
    public Seance getByEventAndDate(Event event, LocalDateTime dateTime) {
        return seanceDAO.getByEventAndDate(event, dateTime);
    }

}
