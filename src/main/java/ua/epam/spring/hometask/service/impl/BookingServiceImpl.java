package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class BookingServiceImpl implements BookingService {

    private DiscountService discountService;
    private TicketDAO ticketDAO;

    public BookingServiceImpl(DiscountService discountService, TicketDAO ticketDAO) {
        this.discountService = discountService;
        this.ticketDAO = ticketDAO;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
                                  @Nonnull Set<Long> seats, Set<Long> vipSeats) {
        int price = 0;
        double discount = discountService.getDiscount(user, event, dateTime, seats, vipSeats);

        switch (event.getRating()) {
            case MID:
                event.setBasePrice(event.getBasePrice() * 1.1);
            case HIGH:
                event.setBasePrice(event.getBasePrice() * 1.2);
        }

        for (long seat : seats) {
            if (vipSeats.contains(seat))
                price += 2 * event.getBasePrice();
            else price += event.getBasePrice();
        }

        return price - discount;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket -> ticketDAO.addTicket(ticket));
    }

    @Nonnull
    @Override
    public List<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDAO.getForEvent(event.getIdevent());
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketDAO.getAll();
    }
}
