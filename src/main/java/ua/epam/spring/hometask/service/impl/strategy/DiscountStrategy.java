package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
@FunctionalInterface
public interface DiscountStrategy {
    double getDiscount(User user, Set<Long> seats, Event event, LocalDateTime date, Set<Long> vipSeats);
}
