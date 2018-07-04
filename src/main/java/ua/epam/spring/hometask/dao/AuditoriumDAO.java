package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class AuditoriumDAO {

    private static List<Auditorium> auditoriums;

    public AuditoriumDAO(List<Auditorium> auditoriumsCons) {
        auditoriums = auditoriumsCons;
    }

    public List<Auditorium> getAll() {
        return auditoriums;
    }

    public Auditorium getByName(String name) {
        Optional<Auditorium> auditorium = auditoriums.stream()
                .filter(item -> item.getName().equals(name)).findFirst();
        return auditorium.orElse(null);
    }
}
