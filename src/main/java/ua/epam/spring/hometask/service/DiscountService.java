package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user  User that buys tickets. Can be <code>null</code>
     * @param event Event that tickets are bought for
     * @param seats Seats that user buys
     * @return discount value from 0 to 100
     */
    Double getDiscount(@Nullable User user, @Nonnull Event event,
                       LocalDateTime date, @Nonnull Set<Long> seats, Set<Long> vipSeats);

}
