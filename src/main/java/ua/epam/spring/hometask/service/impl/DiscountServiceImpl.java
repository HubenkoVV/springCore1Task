package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.impl.strategy.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> strategies;

    public DiscountServiceImpl(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Double getDiscount(@Nullable User user, @Nonnull Event event, LocalDateTime date, @Nonnull Set<Long> seats) {
        List<Double> discounts = new ArrayList<>();
        strategies.forEach(strategies -> discounts.add(strategies.getDiscount(user, seats, event, date)));
        return Collections.max(discounts);
    }
}
