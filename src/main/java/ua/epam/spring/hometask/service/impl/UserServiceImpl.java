package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Seance;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private TicketDAO ticketDAO;

    public UserServiceImpl(UserDAO userDAO, TicketDAO ticketDAO) {
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public User save(@Nonnull User object) {
        object.setIduser(userDAO.addUser(object));
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        userDAO.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDAO.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public List<Ticket> getTickets(User user) {
        return ticketDAO.getForUser(user.getIduser());
    }

    @Override
    public List<Ticket> getTicketsBySeance(User user, Seance seance) {
        return ticketDAO.getForUserBySeance(user.getIduser(), seance.getIdseance());
    }
}
