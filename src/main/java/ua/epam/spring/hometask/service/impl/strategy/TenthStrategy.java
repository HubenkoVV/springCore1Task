package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class TenthStrategy implements DiscountStrategy {

    @Override
    public double getDiscount(User user, Set<Long> seats, Event event, LocalDateTime date) {
        double discount;
        if (user != null) {
            discount = discountForUser(user.getTickets().size(), event.getAuditoriums().get(date), seats
                    , event.getBasePrice());
        } else {
            discount = discountForUser(0, event.getAuditoriums().get(date), seats, event.getBasePrice());
        }
        return discount;
    }

    private double discountForUser(int ticketsUser, Auditorium auditorium, Set<Long> seats, double price) {
        double discount = 0;
        for (long seat : seats) {
            ticketsUser++;
            if (ticketsUser % 10 == 0) {
                if (auditorium.getVipSeats().contains(seat))
                    discount += 2 * price * 0.5;
                else discount += price * 0.5;
            }
        }
        return discount;
    }
}
