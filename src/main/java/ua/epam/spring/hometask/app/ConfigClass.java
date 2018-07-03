package ua.epam.spring.hometask.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.epam.spring.hometask.controller.MainController;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.service.impl.*;
import ua.epam.spring.hometask.service.impl.strategy.BirthdayStrategy;
import ua.epam.spring.hometask.service.impl.strategy.DiscountStrategy;
import ua.epam.spring.hometask.service.impl.strategy.TenthStrategy;
import ua.epam.spring.hometask.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
@Configuration
public class ConfigClass {

    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }

    @Bean
    @Scope("prototype")
    public Ticket ticket() {
        return new Ticket();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    public EventDAO eventDAO() {
        return new EventDAO();
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl(eventDAO());
    }

    @Bean
    public AuditoriumDAO auditoriumDAO() {
        return new AuditoriumDAO();
    }

    @Bean
    public AuditoriumService auditoriumService() {
        return new AuditoriumServiceImpl(auditoriumDAO());
    }

    @Bean
    public BookingService bookingService() {
        return new BookingServiceImpl(discountService());
    }

    @Bean
    public DiscountService discountService() {
        List<DiscountStrategy> strategyList = new ArrayList<>();
        strategyList.add(birth());
        strategyList.add(tenth());
        return new DiscountServiceImpl(strategyList);
    }

    @Bean
    public DiscountStrategy birth() {
        return new BirthdayStrategy();
    }

    @Bean
    public DiscountStrategy tenth() {
        return new TenthStrategy();
    }

    @Bean
    public MainController controller() {
        return new MainController(view(), new Scanner(System.in), userService());
    }

    @Bean
    public View view() {
        return new View();
    }
}
