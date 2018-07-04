package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class BookingServiceImpl implements BookingService {

    private DiscountService discountService;

    public BookingServiceImpl(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        int price = 0;
        double discount = discountService.getDiscount(user, event, dateTime, seats);

        switch (event.getRating()) {
            case MID:
                event.setBasePrice(event.getBasePrice() * 1.1);
            case HIGH:
                event.setBasePrice(event.getBasePrice() * 1.2);
        }

        for (long seat : seats) {
            if (event.getAuditoriums().get(dateTime).getVipSeats().contains(seat))
                price += 2 * event.getBasePrice();
            else price += event.getBasePrice();
        }

        return price - discount;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.addAll(tickets);

        Ticket first = tickets.iterator().next();
        Event event = first.getEvent();

        NavigableMap<LocalDateTime, List<Ticket>> ticketMap = new TreeMap<>();
        ticketMap.put(first.getDateTime(), ticketList);
        event.setTicketsOnDate(ticketMap);

        if (first.getUser() != null) {
            first.getUser().setTickets(new TreeSet<>(tickets));
        }
    }

    @Nonnull
    @Override
    public List<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return event.getTicketsOnDate().get(dateTime);
    }
}
