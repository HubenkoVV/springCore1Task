package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class BirthdayStrategy implements DiscountStrategy {

    @Override
    public double getDiscount(User user, Set<Long> seats, Event event, LocalDateTime date, Set<Long> vipSeats) {
        if (user != null && user.getBirthday().getMonth() == LocalDate.now().getMonth()
                && user.getBirthday().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
            return discountForUser(vipSeats, seats, event.getBasePrice());
        }
        return 0;
    }

    private double discountForUser(Set<Long> vipSeats, Set<Long> seats, double price) {
        double discount = 0;
        for (long seat : seats) {
            if (vipSeats.contains(seat))
                discount += 2 * price * 0.05;
            else discount += price * 0.05;
        }
        return discount;
    }
}
