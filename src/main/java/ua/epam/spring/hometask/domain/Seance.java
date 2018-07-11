package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public class Seance {
    private Long idseance;
    private Long event;
    private LocalDateTime dateTime;
    private Long auditorium;

    public Long getIdseance() {
        return idseance;
    }

    public void setIdseance(Long idseance) {
        this.idseance = idseance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Long auditorium) {
        this.auditorium = auditorium;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seance seance = (Seance) o;

        return idseance.equals(seance.idseance);
    }

    @Override
    public int hashCode() {
        return idseance.hashCode();
    }
}
