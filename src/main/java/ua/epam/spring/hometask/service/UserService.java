package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Seance;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     *
     * @param email Email of the user
     * @return found user or <code>null</code>
     */
    @Nullable
    User getUserByEmail(@Nonnull String email);

    List<Ticket> getTickets(User user);

    List<Ticket> getTicketsBySeance(User user, Seance seance);

}
