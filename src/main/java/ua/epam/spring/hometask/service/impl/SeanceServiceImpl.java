package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.SeanceDAO;
import ua.epam.spring.hometask.domain.Seance;
import ua.epam.spring.hometask.service.SeanceService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/10/2018.
 */
public class SeanceServiceImpl implements SeanceService {

    private SeanceDAO seanceDAO;

    public SeanceServiceImpl(SeanceDAO seanceDAO) {
        this.seanceDAO = seanceDAO;
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

    public List<LocalDateTime> getDatesForEvent(Long id) {
        return seanceDAO.getDatesForEvent(id);
    }

    public Long getAuditorimForEventByDate(Long id, LocalDateTime date) {
        return seanceDAO.getAuditorimForEventByDate(id, date);
    }

}
