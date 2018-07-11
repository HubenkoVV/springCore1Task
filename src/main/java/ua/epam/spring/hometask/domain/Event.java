package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Yuriy_Tkach
 */
public class Event {

    private Long idevent;

    private String name;

    private NavigableSet<LocalDateTime> airDates = new TreeSet<>();

    private double basePrice;

    private EventRating rating;

    private List<Seance> seances = new ArrayList<>();

    private NavigableMap<LocalDateTime, List<Ticket>> ticketsOnDate = new TreeMap<>();

    public Long getIdevent() {
        return idevent;
    }

    public void setIdevent(Long idevent) {
        this.idevent = idevent;
    }

    public NavigableMap<LocalDateTime, List<Ticket>> getTicketsOnDate() {
        return ticketsOnDate;
    }

    public void setTicketsOnDate(NavigableMap<LocalDateTime, List<Ticket>> ticketsOnDate) {
        this.ticketsOnDate = ticketsOnDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<LocalDateTime> getAirDates() {
        return airDates;
    }

    public void setAirDates(NavigableSet<LocalDateTime> airDates) {
        this.airDates = airDates;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return idevent.equals(event.idevent);
    }

    @Override
    public int hashCode() {
        return idevent.hashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", rating=" + rating +
                '}';
    }
}
