package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class EventDAO {

    private static Map<Long, Event> eventMap;

    public EventDAO() {
    }

    public Event save(Event event) {
        long id = event.getId();
        if (!eventMap.containsKey(id)) {
            id = Collections.max(eventMap.keySet()) + 1;
            event.setId(id);
        }
        eventMap.put(id, event);
        return event;
    }

    public void remove(Event event) {
        eventMap.remove(event.getId());
    }

    public Event getById(long id) {
        return eventMap.get(id);
    }

    public List<Event> getAll() {
        return new ArrayList<>(eventMap.values());
    }

    public Event getByName(String name) {
        Optional<Event> event = eventMap.values().stream().filter(item -> item.getName().equals(name)).findFirst();
        return event.orElse(null);
    }


    public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
        Optional<Event> events = eventMap.values().stream().filter(event -> event.airsOnDates(from, to)).findAny();
        return (Set<Event>) events.orElse(null);
    }

    public Set<Event> getNextEvents(LocalDateTime to) {
        Optional<Event> events = eventMap.values().stream().filter(event -> event.getAirDates().stream()
                .anyMatch(dt -> dt.compareTo(LocalDateTime.now()) >= 0
                        && dt.compareTo(to) <= 0)).findAny();
        return (Set<Event>) events.orElse(null);
    }


}
