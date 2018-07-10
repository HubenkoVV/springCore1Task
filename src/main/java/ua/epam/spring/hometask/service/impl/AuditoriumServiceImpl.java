package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.naming.OperationNotSupportedException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDAO auditoriumDAO;

    public AuditoriumServiceImpl(AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        object.setIdauditorium(auditoriumDAO.addAuditorium(object));
        return object;
    }

    @Override
    public void remove(@Nonnull Auditorium object) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Override
    public Auditorium getById(@Nonnull Long id) {
        return auditoriumDAO.getById(id);
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(auditoriumDAO.getAll());
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDAO.getByName(name);
    }

    public Set<Long> getSeatsVIP(Auditorium auditorium) {
        return auditoriumDAO.seatsVIP(auditorium.getIdauditorium());
    }
}
