package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class EventServiceImpl implements EventService {
    private EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Event save(@Nonnull Event object) {
        object.setIdevent(eventDAO.addEvent(object));
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        eventDAO.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventDAO.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventDAO.getAll();
    }


    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventDAO.getByName(name);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return eventDAO.getEventForDateRange(from.atStartOfDay(), to.atStartOfDay());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return eventDAO.getEventForDateRange(LocalDateTime.now(), to);
    }


}
