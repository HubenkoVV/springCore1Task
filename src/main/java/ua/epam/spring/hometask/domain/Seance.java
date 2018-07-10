package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public class Seance {
    private Long event;
    private LocalDateTime dateTime;
    private Long auditorium;

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
}
