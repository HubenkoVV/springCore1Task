package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class AuditoriumDAO {

    private static Map<Long, Auditorium> auditoriumMap;

    public List<Auditorium> getAll() {
        return new ArrayList<>(auditoriumMap.values());
    }

    public Auditorium getByName(String name) {
        Optional<Auditorium> auditorium = auditoriumMap.values().stream()
                .filter(item -> item.getName().equals(name)).findFirst();
        return auditorium.orElse(null);
    }
}
