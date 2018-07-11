package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Seance;
import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public interface SeanceService {

    Seance save(@Nonnull Seance object);

    /**
     * Getting all objects from storage
     *
     * @return collection of objects
     */
    @Nonnull
    List getAll();

    List<LocalDateTime> getDatesForEvent(Long id);

    Long getAuditorimForEventByDate(Long id, LocalDateTime date);

    List<Ticket> getTickets(Seance seance);

    Seance getByEventAndDate(Event event, LocalDateTime dateTime);
}
